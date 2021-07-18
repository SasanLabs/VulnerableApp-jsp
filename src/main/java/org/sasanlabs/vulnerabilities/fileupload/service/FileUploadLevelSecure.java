package org.sasanlabs.vulnerabilities.fileupload.service;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;

public class FileUploadLevelSecure extends AbstractFileUpload {

    private static final String LEVEL = "LEVEL_7";
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpeg");

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
        return AbstractFileUpload.getFileUploadVulnerabilityLevelDefinition(
                LEVEL, "", Variant.SECURE, null);
    }

    @Override
    public boolean validate(FileItem fileItem) throws VulnerableAppException {
        String extension = VulnerableAppUtility.getExtension(fileItem.getName());
        if (ALLOWED_EXTENSIONS.contains(extension)) {
            return true;
        }
        return false;
    }
}
