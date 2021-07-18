package org.sasanlabs.vulnerabilities.fileupload.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppConstants;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.vulnerabilities.fileupload.UploadedFileDetails;
import org.sasanlabs.vulnerableapp.facade.schema.ResourceInformation;
import org.sasanlabs.vulnerableapp.facade.schema.ResourceType;
import org.sasanlabs.vulnerableapp.facade.schema.ResourceURI;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelHint;

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

    static VulnerabilityLevelDefinition getFileUploadVulnerabilityLevelDefinition(
            String level, String description, Variant variant, List<VulnerabilityLevelHint> hints) {
        VulnerabilityLevelDefinition vulnerabilityLevelDefinition =
                new VulnerabilityLevelDefinition();
        vulnerabilityLevelDefinition.setLevel(level);
        vulnerabilityLevelDefinition.setDescription(description);
        vulnerabilityLevelDefinition.setVariant(variant);
        ResourceInformation resourceInformation = new ResourceInformation();
        resourceInformation.setHtmlResource(
                new ResourceURI(
                        false,
                        VulnerableAppConstants.SERVLET_CONTEXT
                                + "/static/templates/FileUpload/LEVEL_1/FileUpload.html"));

        List<ResourceURI> staticResources = new ArrayList<ResourceURI>();
        staticResources.add(
                new ResourceURI(
                        false,
                        VulnerableAppConstants.SERVLET_CONTEXT
                                + "/static/templates/FileUpload/LEVEL_1/FileUpload.js",
                        ResourceType.JAVASCRIPT.name()));
        staticResources.add(
                new ResourceURI(
                        false,
                        VulnerableAppConstants.SERVLET_CONTEXT
                                + "/static/templates/FileUpload/LEVEL_1/FileUpload.css",
                        ResourceType.CSS.name()));
        resourceInformation.setStaticResources(staticResources);
        vulnerabilityLevelDefinition.setResourceInformation(resourceInformation);

        if (hints != null) {
            vulnerabilityLevelDefinition.setHints(hints);
        }
        return vulnerabilityLevelDefinition;
    }

    private File getFileUploadDirectory(String levelDirectory) {
        String tomcatBaseDirectory = System.getProperty("catalina.base");
        File uploadDir =
                new File(
                        tomcatBaseDirectory
                                + File.separator
                                + WEB_APPS
                                + File.separator
                                + VulnerableAppConstants.SERVLET_CONTEXT
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
        if (storeFile.exists()) {
            storeFile.delete();
        }
        try {
            fileItem.write(storeFile);
            UploadedFileDetails uploadedFileDetails =
                    new UploadedFileDetails(
                            VulnerableAppConstants.SERVLET_CONTEXT
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
    public abstract VulnerabilityLevelDefinition getVulnerabilityLevelDefinition();
}
