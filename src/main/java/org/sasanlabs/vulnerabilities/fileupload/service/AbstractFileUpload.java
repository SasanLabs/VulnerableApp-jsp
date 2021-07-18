package org.sasanlabs.vulnerabilities.fileupload.service;

import java.io.File;
import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.vulnerabilities.fileupload.UploadedFileDetails;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;

/**
 * {@link AbstractFileUpload} represents various FileUpload utilities for uploading files.
 *
 * <p>General logic is that every utility is vulnerable to one type of UnrestrictedFileUpload and
 * represents one "level".
 *
 * @author preetkaran20@gmail.com KSASAN
 */
public abstract class AbstractFileUpload {

    private static final String WEB_APPS = "webapps";
    private static final String STATIC_FILES_FOLDER = "static";

    private File getFileUploadDirectory(String levelDirectory) {
        String tomcatBaseDirectory = System.getProperty("catalina.base");
        File uploadDir =
                new File(
                        tomcatBaseDirectory
                                + File.separator
                                + WEB_APPS
                                + File.separator
                                + VulnerableAppUtility.SERVLET_CONTEXT
                                + File.separator
                                + STATIC_FILES_FOLDER
                                + File.separator
                                + levelDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        return uploadDir;
    }

    public UploadedFileDetails validateAndUpload(FileItem fileItem) throws VulnerableAppException {
        if (!this.validate(fileItem)) {
            throw new VulnerableAppException("Validation failed");
        }
        String fileName = new File(fileItem.getName()).getName();
        String filePath =
                this.getFileUploadDirectory(this.getVulnerabilityLevelDefinition().getLevel())
                                .getAbsolutePath()
                        + File.separator
                        + fileName;
        File storeFile = new File(filePath);
        try {
            fileItem.write(storeFile);
            UploadedFileDetails uploadedFileDetails =
                    new UploadedFileDetails(
                            VulnerableAppUtility.SERVLET_CONTEXT
                                    + File.separator
                                    + "static"
                                    + File.separator
                                    + this.getVulnerabilityLevelDefinition().getLevel()
                                    + File.separator
                                    + fileName);
            return uploadedFileDetails;
        } catch (Exception e) {
            throw new VulnerableAppException(e);
        }
    }

    /**
     * This method is used to validate the uploaded file.
     *
     * @param fileItem
     * @return {@code True} if validation successful else {@code false}
     * @throws VulnerableAppException
     */
    public abstract boolean validate(FileItem fileItem) throws VulnerableAppException;

    /**
     * Provides the details about the Vulnerability Level which this fileupload utility represents.
     *
     * @return {@code VulnerabilityDefinitionResponseBean.LevelResponseBean}
     */
    public abstract VulnerabilityLevelDefinition
            getVulnerabilityLevelDefinition();
}
