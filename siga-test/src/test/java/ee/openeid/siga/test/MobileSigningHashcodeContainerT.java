package ee.openeid.siga.test;

import ee.openeid.siga.test.model.SigaApiFlow;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static ee.openeid.siga.test.TestData.*;
import static ee.openeid.siga.test.utils.RequestBuilder.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class MobileSigningHashcodeContainerT extends TestBase {

    private SigaApiFlow flow;

    @Before
    public void setUp() {
        flow = SigaApiFlow.buildForTestClient1Service1();
    }

    @Test
    public void signWithMidSuccessfully() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019906", "+37200000766", "LT"));
        pollForMidSigning(flow);

        Response validationResponse = getValidationReportForContainerInSession(flow);

        validationResponse.then()
                .statusCode(200)
                .body("validationConclusion.validSignaturesCount", equalTo(1));
    }

    @Ignore //TODO: OCSP is not fetched for some reason, needs investigation
    @Test
    public void signWithLtMidSuccessfully() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("50001018865", "+37060000666", "LT"));
        pollForMidSigning(flow);

        Response response = getValidationReportForContainerInSession(flow);

        response.then()
                .statusCode(200)
                .body("validationConclusion.validSignaturesCount", equalTo(1));
    }

    @Test
    public void mobileIdNotActivated() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());

        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019928", "+37200000366", "LT"));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(CLIENT_EXCEPTION));
    }

    @Test
    public void mobileIdCertificateRevoked() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());

        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019939", "+37200000266", "LT"));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(CLIENT_EXCEPTION));
    }

    @Test
    public void mobileIdSendingFailed() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019947", "+37207110066", "LT"));

        Response response = pollForMidSigning(flow);

        response.then()
                .statusCode(200)
                .body(MID_STATUS, equalTo(SENDING_ERROR));
    }

    @Test
    public void mobileIdUserCancel() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019950", "+37201100266", "LT"));

        Response response = pollForMidSigning(flow);

        response.then()
                .statusCode(200)
                .body(MID_STATUS, equalTo(USER_CANCEL));
    }

    @Test
    public void mobileIdSignatureNotValid() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019961", "+37200000666", "LT"));

        Response response = pollForMidSigning(flow);

        response.then()
                .statusCode(200)
                .body(MID_STATUS, equalTo(NOT_VALID));
    }

    @Test
    public void mobileIdSimError() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019972", "+37201200266", "LT"));

        Response response = pollForMidSigning(flow);

        response.then()
                .statusCode(200)
                .body(MID_STATUS, equalTo(SIM_ERROR));
    }

    @Test
    public void mobileIdPhoneNotInNetwork() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019983", "+37213100266", "LT"));

        Response response = pollForMidSigning(flow);

        response.then()
                .statusCode(200)
                .body(MID_STATUS, equalTo(PHONE_ABSENT));
    }

    @Test
    public void mobileIdUserTimeout() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("50001018908", "+37066000266", "LT"));

        Response response = pollForMidSigning(flow);

        response.then()
                .statusCode(200)
                .body(MID_STATUS, equalTo(EXPIRED_TRANSACTION));
    }

    @Test
    public void mobileIdUserCancelAndRetries() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019950", "+37201100266", "LT"));
        pollForMidSigning(flow);
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019906", "+37200000766", "LT"));
        pollForMidSigning(flow);

        Response response = getValidationReportForContainerInSession(flow);

        response.then()
                .statusCode(200)
                .body("validationConclusion.validSignaturesCount", equalTo(1))
                .body("validationConclusion.signaturesCount", equalTo(1));
    }

    @Test
    public void mobileIdUserTimeoutsAndRetries() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("50001018908", "+37066000266", "LT"));
        pollForMidSigning(flow);
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019906", "+37200000766", "LT"));
        pollForMidSigning(flow);

        Response response = getValidationReportForContainerInSession(flow);

        response.then()
                .statusCode(200)
                .body("validationConclusion.validSignaturesCount", equalTo(1))
                .body("validationConclusion.signaturesCount", equalTo(1));
    }

    @Test
    public void missingPersonIdentifier() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("", "+37200000766", "LT"));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Ignore //TODO: Should we check the format before sending to DDS? SIGARIA-62
    @Test
    public void invalidPersonIdentifierFormat() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("P:NO-23a.31,23", "+37200000766", "LT"));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Test
    public void missingPhoneNumber() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019906", "", "LT"));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Ignore //TODO: Should we check the format before sending to DDS? SIGARIA-62
    @Test
    public void invalidPhoneNumberFormat() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019906", "-/ssasa", "LT"));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Test
    public void missingCountryInRequest() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequest("60001019906", "+37200000766", "","EST", "LT", null, null, null, null, null, null));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Ignore
    @Test
    public void invalidCountryInRequest() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequest("60001019906", "+37200000766", "QE","EST", "LT", null, null, null, null, null, null));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Test
    public void missingLanguageInRequest() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequest("60001019906", "+37200000766", "EE","", "LT", null, null, null, null, null, null));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Ignore //TODO: SIGARIA-62
    @Test
    public void invalidLanguageInRequest() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequest("60001019906", "+37200000766", "EE","SOM", "LT", null, null, null, null, null, null));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Test
    public void missingProfileInRequest() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequest("60001019906", "+37200000766", "EE","EST", "", null, null, null, null, null, null));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Test
    public void invalidProfileInRequest() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequest("60001019906", "+37200000766", "EE","EST", "T", null, null, null, null, null, null));

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

  //  @Ignore //TODO: SIGARIA-62
    @Test
    public void maximumDataInRequest() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        Response response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequest("60001019906", "+37200000766", "EE","EST", "LT", "message", "Tallinn", "Harjumaa", "75544", "Estonia", null));
        pollForMidSigning(flow);
        getHashcodeContainer(flow);
        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(INVALID_REQUEST));
    }

    @Test
    public void midStatusRequestForOtherUserContainer() throws Exception {
        postCreateHashcodeContainer(flow, hashcodeContainersDataRequestWithDefault());
        postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019906", "+37200000766", "LT"));

        flow.setServiceUuid(SERVICE_UUID_2);
        flow.setServiceSecret(SERVICE_SECRET_2);

        Response response = getHashcodeMidSigningInSession(flow);

        response.then()
                .statusCode(400)
                .body(ERROR_CODE, equalTo(RESOURCE_NOT_FOUND));
    }

}