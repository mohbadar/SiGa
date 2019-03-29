package ee.openeid.siga.common.event;

public enum SigaEventName {
    REQUEST,
    AUTHENTICATION,
    HC_UPLOAD_CONTAINER,
    HC_CREATE_CONTAINER,
    HC_VALIDATE_CONTAINER,
    HC_VALIDATE_CONTAINER_BY_ID,
    HC_REMOTE_SIGNING_INIT,
    HC_REMOTE_SIGNING_FINISH,
    HC_REMOTE_SIGNING_CANCEL,
    HC_MOBILE_ID_SIGNING_INIT,
    HC_MOBILE_ID_SIGNING_STATUS,
    HC_MOBILE_ID_SIGNING_CANCEL,
    HC_SMART_ID_SIGNING_INIT,
    HC_SMART_ID_SIGNING_STATUS,
    HC_SMART_ID_SIGNING_CANCEL,
    HC_GET_SIGNATURES_LIST,
    HC_GET_SIGNATURE,
    HC_ADD_DATAFILE,
    HC_GET_DATAFILES_LIST,
    HC_GET_DATAFILE,
    HC_GET_CONTAINER,
    HC_DELETE_CONTAINER,
    UPLOAD_CONTAINER,
    CREATE_CONTAINER,
    VALIDATE_CONTAINER,
    VALIDATE_CONTAINER_BY_ID,
    REMOTE_SIGNING_INIT,
    REMOTE_SIGNING_FINISH,
    REMOTE_SIGNING_CANCEL,
    MOBILE_ID_SIGNING_INIT,
    MOBILE_ID_SIGNING_STATUS,
    MOBILE_ID_SIGNING_CANCEL,
    SMART_ID_SIGNING_INIT,
    SMART_ID_SIGNING_STATUS,
    SMART_ID_SIGNING_CANCEL,
    GET_SIGNATURES_LIST,
    GET_SIGNATURE,
    ADD_DATAFILE,
    ET_DATAFILES_LIST,
    GET_DATAFILE,
    GET_CONTAINER,
    DELETE_CONTAINER,
    OCSP,
    OCSP_AIA,
    OCSP_TM,
    TSA_REQUEST,
    DDS_GET_MOBILE_CERTIFICATE,
    DDS_MOBILE_SIGN_HASH,
    DDS_GET_MOBILE_SIGN_HASH_STATUS,
    MID_CERTIFICATE_REQUEST,
    MID_INIT_SIGNING,
    MID_GET_SIGNING_STATUS;

    public enum ErrorCode {
        AUTHENTICATION_ERROR, TSA_REQUEST_ERROR, OCSP_REQUEST_ERROR
    }

    public enum EventParam {
        OCSP_URL, TSA_URL, OCSP_SUBJECT_CA, TSA_SUBJECT_CA
    }
}
