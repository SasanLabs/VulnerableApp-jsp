package org.sasanlabs.vulnerabilities.fileupload;

/**
 * {@link UploadedFileDetails} represents the details about the uploaded file.
 *
 * @author KSASAN preetkaran20@gmail.com
 */
public class UploadedFileDetails {

    private String fileNameWithPath;

    public UploadedFileDetails(String fileNameWithPath) {
        this.fileNameWithPath = fileNameWithPath;
    }

    public String getFileNameWithPath() {
        return fileNameWithPath;
    }
}
