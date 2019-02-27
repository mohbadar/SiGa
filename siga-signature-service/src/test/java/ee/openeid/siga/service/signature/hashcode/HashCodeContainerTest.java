package ee.openeid.siga.service.signature.hashcode;

import ee.openeid.siga.common.HashCodeDataFile;
import ee.openeid.siga.common.SignatureHashCodeDataFile;
import ee.openeid.siga.common.SignatureWrapper;
import ee.openeid.siga.common.exception.SignatureExistsException;
import ee.openeid.siga.service.signature.test.HashCodeContainerFilesHolder;
import ee.openeid.siga.service.signature.test.RequestUtil;
import ee.openeid.siga.service.signature.test.TestUtil;
import ee.openeid.siga.service.signature.util.ContainerUtil;
import ee.openeid.siga.webapp.json.CreateHashCodeContainerRequest;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

import static ee.openeid.siga.service.signature.test.RequestUtil.SIGNED_HASHCODE;

public class HashCodeContainerTest {

    @Test
    public void validHashCodeContainerCreation() throws IOException {
        CreateHashCodeContainerRequest request = RequestUtil.getHashCodeCreateContainerRequest();
        HashCodeContainer hashCodeContainer = new HashCodeContainer();
        request.getDataFiles().forEach(dataFile -> hashCodeContainer.addDataFile(ContainerUtil.transformDataFileToHashCodeDataFile(dataFile)));
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            hashCodeContainer.save(outputStream);
            HashCodeContainerFilesHolder hashCodeContainerFilesHolder = TestUtil.getContainerFiles(((ByteArrayOutputStream) outputStream).toByteArray());
            Assert.assertEquals(TestUtil.MIMETYPE, hashCodeContainerFilesHolder.getMimeTypeContent());
            Assert.assertEquals(TestUtil.MANIFEST_CONTENT, hashCodeContainerFilesHolder.getManifestContent());
            Assert.assertEquals(TestUtil.HASHCODES_SHA256_CONTENT, hashCodeContainerFilesHolder.getHashCodesSha256Content());
            Assert.assertEquals(TestUtil.HASHCODES_SHA512_CONTENT, hashCodeContainerFilesHolder.getHashCodesSha512Content());
        }
    }

    @Test
    public void validHashCodeContainerOpening() throws IOException, URISyntaxException {
        HashCodeContainer hashCodeContainer = new HashCodeContainer();
        InputStream inputStream = TestUtil.getFileInputStream(SIGNED_HASHCODE);
        hashCodeContainer.open(inputStream);
        Assert.assertEquals(1, hashCodeContainer.getSignatures().size());
        Assert.assertEquals(2, hashCodeContainer.getDataFiles().size());

        List<SignatureHashCodeDataFile> signatureDataFiles = hashCodeContainer.getSignatures().get(0).getDataFiles();
        Assert.assertEquals(2, signatureDataFiles.size());
        Assert.assertEquals("test.txt", signatureDataFiles.get(0).getFileName());
        Assert.assertEquals("SHA256", signatureDataFiles.get(0).getHashAlgo());
        Assert.assertEquals("test1.txt", signatureDataFiles.get(1).getFileName());
        Assert.assertEquals("SHA256", signatureDataFiles.get(1).getHashAlgo());
    }


    @Test(expected = SignatureExistsException.class)
    public void couldNotAddDataFileWhenSignatureExists() throws IOException, URISyntaxException {
        HashCodeContainer hashCodeContainer = new HashCodeContainer();
        InputStream inputStream = TestUtil.getFileInputStream(SIGNED_HASHCODE);
        hashCodeContainer.open(inputStream);
        HashCodeDataFile hashCodeDataFile = new HashCodeDataFile();
        hashCodeDataFile.setFileName("randomFile.txt");
        hashCodeDataFile.setFileHashSha256("asdasd=");
        hashCodeContainer.addDataFile(hashCodeDataFile);
    }

    @Test
    public void validHashCodeContainerAddedNewData() throws IOException, URISyntaxException {
        HashCodeContainer hashCodeContainer = new HashCodeContainer();
        InputStream inputStream = TestUtil.getFileInputStream(SIGNED_HASHCODE);
        hashCodeContainer.open(inputStream);
        SignatureWrapper signature = hashCodeContainer.getSignatures().get(0);

        hashCodeContainer.getSignatures().remove(0);
        HashCodeDataFile hashCodeDataFile = new HashCodeDataFile();
        hashCodeDataFile.setFileName("randomFile.txt");
        hashCodeDataFile.setFileHashSha256("asdasd=");
        hashCodeDataFile.setFileSize(10);
        hashCodeContainer.addDataFile(hashCodeDataFile);

        hashCodeContainer.addSignature(signature);
        Assert.assertEquals(1, hashCodeContainer.getSignatures().size());
        Assert.assertEquals(3, hashCodeContainer.getDataFiles().size());
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            hashCodeContainer.save(outputStream);
            HashCodeContainer newContainer = new HashCodeContainer();
            newContainer.open(new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray()));
            Assert.assertEquals(1, newContainer.getSignatures().size());
            Assert.assertEquals(3, newContainer.getDataFiles().size());
        }
    }
}
