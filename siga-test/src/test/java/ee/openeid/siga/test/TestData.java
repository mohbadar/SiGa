package ee.openeid.siga.test;

public class TestData {

    // Endpoints
    public static final String HASHCODE_CONTAINERS = "/hashcodecontainers";
    public static final String VALIDATIONREPORT = "/validationreport";
    public static final String UPLOAD = "/upload";
    public static final String REMOTE_SIGNING = "/remotesigning";
    public static final String MID_SIGNING = "/mobileidsigning";
    public static final String STATUS = "/status";


    // Headers for HMAC authentication
    public static final String X_AUTHORIZATION_TIMESTAMP = "X-Authorization-Timestamp";
    public static final String X_AUTHORIZATION_SERVICE_UUID = "X-Authorization-ServiceUUID";
    public static final String X_AUTHORIZATION_SIGNATURE = "X-Authorization-Signature";
    public static final String X_AUTHORIZATION_HMAC_ALGO = "X-Authorization-Hmac-Algorithm";

    // User information
    public static final String SERVICE_UUID = "a7fd7728-a3ea-4975-bfab-f240a67e894f";
    public static final String SERVICE_SECRET = "746573745365637265744b6579303031";

    // Hashcode datafile mock strings
    public static final String DEFAULT_SHA256_DATAFILE = "RnKZobNWVy8u92sDL4S2j1BUzMT5qTgt6hm90TfAGRo";
    public static final String DEFAULT_SHA512_DATAFILE = "hQVz9wirVZNvP/q3HoaW8nu0FfvrGkZinhADKE4Y4j/dUuGfgONfR4VYdu0p/dj/yGH0qlE0FGsmUB2N3oLuhA==";
    public static final String DEFAULT_FILENAME = "test.txt";
    public static final String DEFAULT_FILESIZE = "745";

    // Signing mock strings
    public static final String SIGNER_CERT_PEM = "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlENmpDQ0EwMmdBd0lCQWdJUVIrcWNWRnhZRjFwY1N5L1FHRW5NVmpBS0JnZ3Foa2pPUFFRREJEQmdNUXN3DQpDUVlEVlFRR0V3SkZSVEViTUJrR0ExVUVDZ3dTVTBzZ1NVUWdVMjlzZFhScGIyNXpJRUZUTVJjd0ZRWURWUVJoDQpEQTVPVkZKRlJTMHhNRGMwTnpBeE16RWJNQmtHQTFVRUF3d1NWRVZUVkNCdlppQkZVMVJGU1VReU1ERTRNQjRYDQpEVEU1TURFeU5URTFORGd6TVZvWERUSTBNREV5TlRJeE5UazFPVm93ZnpFTE1Ba0dBMVVFQmhNQ1JVVXhLakFvDQpCZ05WQkFNTUlVckRsVVZQVWtjc1NrRkJTeTFMVWtsVFZFcEJUaXd6T0RBd01UQTROVGN4T0RFUU1BNEdBMVVFDQpCQXdIU3NPVlJVOVNSekVXTUJRR0ExVUVLZ3dOU2tGQlN5MUxVa2xUVkVwQlRqRWFNQmdHQTFVRUJSTVJVRTVQDQpSVVV0TXpnd01ERXdPRFUzTVRnd2RqQVFCZ2NxaGtqT1BRSUJCZ1VyZ1FRQUlnTmlBQVRieUNxOTVTV0NRVHIrDQpiNU1YeFJMVEhZSEpIQ2dhTG9ybmxyRjlqK3E2YUZDREZMZ29OdjcweXcvc0hZcDJGUTB5Unl3RzJ2RndEQ0xBDQo1dkFDUExTVlBHeU92WXg3ZmlYODR1U3BQbzZmY05sd1EyNWNvTmZwVUlJdWgrVDZNd3VqZ2dHck1JSUJwekFKDQpCZ05WSFJNRUFqQUFNQTRHQTFVZER3RUIvd1FFQXdJR1FEQklCZ05WSFNBRVFUQS9NRElHQ3lzR0FRUUJnNUVoDQpBUUlCTUNNd0lRWUlLd1lCQlFVSEFnRVdGV2gwZEhCek9pOHZkM2QzTG5OckxtVmxMME5RVXpBSkJnY0VBSXZzDQpRQUVDTUIwR0ExVWREZ1FXQkJUSWdFYWYwd1NQWlNXaWhqTHV5VE5tem00RFd6Q0JpZ1lJS3dZQkJRVUhBUU1FDQpmakI4TUFnR0JnUUFqa1lCQVRBSUJnWUVBSTVHQVFRd0V3WUdCQUNPUmdFR01Ba0dCd1FBamtZQkJnRXdVUVlHDQpCQUNPUmdFRk1FY3dSUlkvYUhSMGNITTZMeTl6YXk1bFpTOWxiaTl5WlhCdmMybDBiM0o1TDJOdmJtUnBkR2x2DQpibk10Wm05eUxYVnpaUzF2WmkxalpYSjBhV1pwWTJGMFpYTXZFd0pGVGpBZkJnTlZIU01FR0RBV2dCVEFoSmtwDQp4RTZmT3dJMDlwbmhDbFlBQ0NrK2V6QnpCZ2dyQmdFRkJRY0JBUVJuTUdVd0xBWUlLd1lCQlFVSE1BR0dJR2gwDQpkSEE2THk5aGFXRXVaR1Z0Ynk1emF5NWxaUzlsYzNSbGFXUXlNREU0TURVR0NDc0dBUVVGQnpBQ2hpbG9kSFJ3DQpPaTh2WXk1emF5NWxaUzlVWlhOMFgyOW1YMFZUVkVWSlJESXdNVGd1WkdWeUxtTnlkREFLQmdncWhrak9QUVFEDQpCQU9CaWdBd2dZWUNRU1BCSFlPMk8vYUxtcit2cU1sRVNKcklZM2dkdFduaThoZDRwaElsNWZSM3VpUWFRdnRODQplR0JJenJHdmRxZ1JKbVlnK0h2c2tRYi9MYXE3WGpwK2NncWtBa0VYOSt4L1MzSC9TLytuL25vZ2ZnUlNQNUpDDQp3WUF3MDJ6VFJMM01LTHBaMUFPZjhpMWlHdnBISTlTNml5WGNEaGg2aE04c2xEZzdFSzNLeU53ZmtNTGg1QT09DQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tDQo";


    // Response strings
    public static final String CONTAINER_ID = "containerId";

    // Error response strings
    public static final String ERROR_CODE = "errorCode";

    // Error codes
    public static final String SESSION_NOT_FOUND = "SESSION_NOT_FOUND";

}
