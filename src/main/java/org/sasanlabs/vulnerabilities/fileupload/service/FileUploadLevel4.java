package org.sasanlabs.vulnerabilities.fileupload.service;

import static org.sasanlabs.framework.VulnerableAppConstants.JSP_EXTENSIONS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;

public class FileUploadLevel4 extends FileUploadLevel2 {

    private static final String LEVEL = "LEVEL_4";

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
        return AbstractFileUpload.getFileUploadVulnerabilityLevelDefinition(
                LEVEL, "", Variant.UNSECURE, null);
    }

    @Override
    public boolean validate(FileItem fileItem) throws VulnerableAppException {
        try {
            // Check for validating Scriptlet
            if (JSP_EXTENSIONS.contains(VulnerableAppUtility.getExtension(fileItem.getName()))) {
                String result =
                        IOUtils.toString(fileItem.getInputStream(), StandardCharsets.UTF_8.name());
                if (result.contains("<%") && result.contains("%>")) {
                    return false;
                }
            }
        } catch (IOException e) {
            throw new VulnerableAppException(e);
        }
        return super.validate(fileItem);
    }
}
