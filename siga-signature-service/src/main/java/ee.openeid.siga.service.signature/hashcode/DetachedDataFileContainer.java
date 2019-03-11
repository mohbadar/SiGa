package ee.openeid.siga.service.signature.hashcode;

import ee.openeid.siga.common.HashCodeDataFile;
import ee.openeid.siga.common.SignatureWrapper;
import ee.openeid.siga.common.exception.InvalidRequestException;
import ee.openeid.siga.common.exception.SignatureExistsException;
import ee.openeid.siga.service.signature.util.ContainerUtil;
import eu.europa.esig.dss.DSSDocument;
import eu.europa.esig.dss.DigestDocument;
import eu.europa.esig.dss.MimeType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static ee.openeid.siga.service.signature.hashcode.DetachedDataFileContainerCreator.SIGNATURE_FILE_PREFIX;

public class DetachedDataFileContainer {

    private List<HashCodeDataFile> dataFiles = new ArrayList<>();
    private List<SignatureWrapper> signatures = new ArrayList<>();

    public void save(OutputStream outputStream) {
        createHashCodeContainer(outputStream);
    }

    public void open(InputStream inputStream) {
        try {
            ZipInputStream zipStream = new ZipInputStream(inputStream);
            ZipEntry entry;
            boolean isValidZip = false;
            while ((entry = zipStream.getNextEntry()) != null) {
                operateWithEntry(entry, zipStream);
                isValidZip = true;
            }
            zipStream.close();
            if (!isValidZip) {
                throw new InvalidRequestException("Invalid container");
            }
        } catch (IOException e) {
            throw new InvalidRequestException("Unable to open hashcode container");
        }
    }

    private void createHashCodeContainer(OutputStream outputStream) {
        DetachedDataFileContainerCreator hashCodeContainerCreator = new DetachedDataFileContainerCreator(outputStream);
        hashCodeContainerCreator.writeMimeType();
        hashCodeContainerCreator.writeManifest(convertDataFiles());
        hashCodeContainerCreator.writeHashCodeFiles(dataFiles);
        hashCodeContainerCreator.writeSignatures(signatures);

        hashCodeContainerCreator.finalizeZipFile();
    }

    private void operateWithEntry(ZipEntry entry, ZipInputStream zipStream) throws IOException {
        String entryName = entry.getName();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] byteBuff = new byte[4096];
            int bytesRead;
            while ((bytesRead = zipStream.read(byteBuff)) != -1) {
                out.write(byteBuff, 0, bytesRead);
            }
            if (entryName.startsWith(SIGNATURE_FILE_PREFIX)) {
                signatures.add(createSignatureWrapper(out.toByteArray()));
            } else if (entryName.startsWith(HashCodesDataFile.HASHCODES_PREFIX)) {
                HashCodesDataFileParser parser = new HashCodesDataFileParser(out.toByteArray());
                addDataFileEntries(parser.getEntries(), entryName);
            }
            zipStream.closeEntry();
        }
    }

    private SignatureWrapper createSignatureWrapper(byte[] signature) {

        SignatureDataFilesParser parser = new SignatureDataFilesParser(signature);
        Map<String, String> dataFiles = parser.getEntries();

        SignatureWrapper signatureWrapper = new SignatureWrapper();
        signatureWrapper.setSignature(signature);
        ContainerUtil.addSignatureDataFilesEntries(signatureWrapper, dataFiles);
        return signatureWrapper;
    }

    private void addDataFileEntries(Map<String, HashCodesEntry> entries, String entryName) {
        entries.forEach((file, hashCodesEntry) -> {
            Optional<HashCodeDataFile> existingDataFile = dataFiles.stream().filter(dataFile -> dataFile.getFileName().equals(file)).findAny();
            if (existingDataFile.isPresent()) {
                if (HashCodesDataFile.HASHCODES_SHA256.equals(entryName)) {
                    existingDataFile.get().setFileHashSha256(hashCodesEntry.getHash());
                } else if (HashCodesDataFile.HASHCODES_SHA512.equals(entryName)) {
                    existingDataFile.get().setFileHashSha512(hashCodesEntry.getHash());
                }
            } else {
                HashCodeDataFile hashCodeDataFile = new HashCodeDataFile();
                hashCodeDataFile.setFileName(file);
                hashCodeDataFile.setFileSize(hashCodesEntry.getSize());
                if (HashCodesDataFile.HASHCODES_SHA256.equals(entryName)) {
                    hashCodeDataFile.setFileHashSha256(hashCodesEntry.getHash());
                } else if (HashCodesDataFile.HASHCODES_SHA512.equals(entryName)) {
                    hashCodeDataFile.setFileHashSha512(hashCodesEntry.getHash());
                }
                dataFiles.add(hashCodeDataFile);
            }
        });
    }

    private List<org.digidoc4j.DataFile> convertDataFiles() {
        return dataFiles.stream().map(d -> {
            DSSDocument dssDocument = new DigestDocument();
            dssDocument.setMimeType(MimeType.BINARY);
            dssDocument.setName(d.getFileName());
            org.digidoc4j.DataFile dataFile = new org.digidoc4j.DataFile();
            dataFile.setDocument(dssDocument);
            return dataFile;

        }).collect(Collectors.toList());
    }

    public List<SignatureWrapper> getSignatures() {
        return signatures;
    }

    public List<HashCodeDataFile> getDataFiles() {
        return dataFiles;
    }

    public void addSignature(SignatureWrapper signature) {
        signatures.add(signature);
    }

    public void addDataFile(HashCodeDataFile dataFile) {
        if (signatures.size() > 0)
            throw new SignatureExistsException("Unable to add data file when signature exists");
        dataFiles.add(dataFile);
    }
}