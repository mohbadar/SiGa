package ee.openeid.siga.test.statistics;

import ee.openeid.siga.test.model.SigaApiFlow;
import ee.openeid.siga.webapp.json.CreateHashcodeContainerMobileIdSigningResponse;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.json.JSONException;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static ee.openeid.siga.common.event.SigaEvent.EventType.FINISH;
import static ee.openeid.siga.common.event.SigaEvent.EventType.START;
import static ee.openeid.siga.common.event.SigaEventName.*;
import static ee.openeid.siga.test.TestData.*;
import static ee.openeid.siga.test.utils.RequestBuilder.hashcodeContainerRequest;
import static ee.openeid.siga.test.utils.RequestBuilder.hashcodeMidSigningRequestWithDefault;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class MobileIdSigningEventsT extends StatisticsBaseT {

    static {
        NR_OF_CONTAINERS_GENERATED = 3;
    }

    @Test
    public void test0_shouldPassMobileSigningFlow() throws JSONException, NoSuchAlgorithmException, InvalidKeyException, IOException, InterruptedException {
        SigaApiFlow service1 = SigaApiFlow.buildForTestClient1Service1();
        mobileSigningFlowFor(service1);
        SigaApiFlow service12 = SigaApiFlow.buildForTestClient1Service2();
        mobileSigningFlowFor(service12);
        SigaApiFlow service3 = SigaApiFlow.buildForTestClient2Service3();
        mobileSigningFlowFor(service3);

        NR_OF_CONTAINERS_GENERATED = containerIds.size();
    }

    private void mobileSigningFlowFor(SigaApiFlow flow) throws InvalidKeyException, NoSuchAlgorithmException, JSONException, IOException, InterruptedException {
        Response response = postUploadHashcodeContainer(flow, hashcodeContainerRequest(DEFAULT_HASHCODE_CONTAINER));
        assertThat(response.statusCode(), equalTo(200));
        String containerId = response.getBody().path(CONTAINER_ID).toString();
        flow.setContainerId(containerId);
        assertThat(containerId.length(), equalTo(36));
        containerIds.add(containerId);

        response = getHashcodeSignatureList(flow);
        assertThat(response.statusCode(), equalTo(200));
        assertEquals("id-a9fae00496ae203a6a8b92adbe762bd3", response.getBody().path("signatures[0].id"));

        response = postHashcodeMidSigningInSession(flow, hashcodeMidSigningRequestWithDefault("60001019906", "+37200000766", "LT"));
        String signatureId = response.as(CreateHashcodeContainerMobileIdSigningResponse.class).getGeneratedSignatureId();
        response = pollForMidSigning(flow, signatureId);
        assertThat(response.statusCode(), equalTo(200));

        response = getValidationReportForContainerInSession(flow);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.getBody().path(REPORT_VALID_SIGNATURES_COUNT), equalTo(2));

        response = deleteHashcodeContainer(flow);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.getBody().path(RESULT), equalTo("OK"));
    }

    @Test
    public void test1_queryResultShouldEqual_HcUploadContainerRequestsMade() {
        await().atMost(ELASTIC_QUERY_TIMEOUT, SECONDS).with().pollInterval(ELASTIC_QUERY_POLL_INTERVAL, SECONDS).until(() -> {
            QueryBuilder query = createQueryForSuccessEvent(HC_UPLOAD_CONTAINER);
            SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
            return checkSearchResponse(response);
        });
    }

    @Test
    public void test2_queryResultShouldEqual_HcGetSignatureListRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(HC_GET_SIGNATURES_LIST);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        checkSearchResponse(response);
    }

    @Test
    public void test3_queryResultShouldEqual_HcMobileIdSigningInitRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(HC_MOBILE_ID_SIGNING_INIT);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        checkSearchResponse(response);
    }

    @Test
    public void test4_queryResultShouldEqual_DdsGetMobileCertificatesRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(DDS_GET_MOBILE_CERTIFICATE, START);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.PERSON_IDENTIFIER, "60001019906"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.COUNTRY, "EE"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.PHONE_NR, "+37200000766"));

        query = createQueryForSuccessEvent(DDS_GET_MOBILE_CERTIFICATE, FINISH);
        response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.DDS_RESPONSE_CODE, "OK"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.SIGN_CERT_STATUS, "OK"));
    }

    @Test
    public void test5_queryResultShouldEqual_DdsMobileSignHashRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(DDS_MOBILE_SIGN_HASH, START);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.PERSON_IDENTIFIER, "60001019906"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.RELYING_PARTY_NAME, "Testimine"));

        query = createQueryForSuccessEvent(DDS_MOBILE_SIGN_HASH, FINISH);
        response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.DDS_RESPONSE_CODE, "OK"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParametersWithAnyValue(response, EventParam.DDS_SESSION_ID));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.PERSON_IDENTIFIER, "60001019906"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.RELYING_PARTY_NAME, "Testimine"));

    }

    @Test
    public void test6_queryResultShouldEqual_HcMobileIdSigningStatusRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(HC_MOBILE_ID_SIGNING_STATUS);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED * 2, response.getHits().totalHits);
        assertEquals(NR_OF_CONTAINERS_GENERATED * 2, countMatchingContainerIds(response));
    }

    @Test
    public void test7_queryResultShouldEqual_DdsGetMobileSignHashStatusRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(DDS_GET_MOBILE_SIGN_HASH_STATUS, new ImmutablePair<>("stats.dds_response_code", "OUTSTANDING_TRANSACTION"));
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParametersWithAnyValue(response, EventParam.DDS_SESSION_ID));
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);

        query = createQueryForSuccessEvent(DDS_GET_MOBILE_SIGN_HASH_STATUS, new ImmutablePair<>("stats.dds_response_code", "SIGNATURE"));
        response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParametersWithAnyValue(response, EventParam.DDS_SESSION_ID));
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);
    }

    @Test
    public void test8_queryResultShouldEqual_FinalizeSignatureRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(FINALIZE_SIGNATURE);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParametersWithAnyValue(response, EventParam.SIGNATURE_ID));
    }

    @Test
    public void test9_queryResultShouldEqual_TsaRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(TSA_REQUEST);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.REQUEST_URL, "http://demo.sk.ee/tsa"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.ISSUING_CA, "C=EE,O=AS Sertifitseerimiskeskus,CN=TEST of EE Certification Centre Root CA,E=pki@sk.ee"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);
    }

    @Test
    public void test_10_queryResultShouldEqual_OcspRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(OCSP_REQUEST);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.REQUEST_URL, "http://aia.demo.sk.ee/esteid2015"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, countMatchingParameters(response, EventParam.ISSUING_CA, "C=EE,O=AS Sertifitseerimiskeskus,2.5.4.97=NTREE-10747013,CN=TEST of ESTEID-SK 2015"));
        assertEquals(NR_OF_CONTAINERS_GENERATED, response.getHits().totalHits);
    }

    @Test
    public void test_11_queryResultShouldEqual_HcValidateContainerByIdRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(HC_VALIDATE_CONTAINER_BY_ID);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        checkSearchResponse(response);
    }

    @Test
    public void test_12_queryResultShouldEqual_HcDeleteContainerRequestsMade() {
        QueryBuilder query = createQueryForSuccessEvent(HC_DELETE_CONTAINER);
        SearchResponse response = prepareSearchRequestFor(query).execute().actionGet();
        checkSearchResponse(response);
    }


}
