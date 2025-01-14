package ee.openeid.siga.service.signature.test;

import lombok.Data;

@Data
public class DetachedDataFileContainerFilesHolder {
    private String mimeTypeContent;
    private String manifestContent;
    private String hashcodesSha256Content;
    private String hashcodesSha512Content;
}
