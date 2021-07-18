package org.sasanlabs.vulnerabilities.fileupload.service;

import static org.sasanlabs.framework.VulnerableAppConstants.JSP_EXTENSIONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.i18n.Messages;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelHint;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityType;

public class FileUploadLevel6 extends FileUploadLevel2 {
    private static final String LEVEL = "LEVEL_6";

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
        List<VulnerabilityLevelHint> hints = new ArrayList<VulnerabilityLevelHint>();
        hints.add(
                new VulnerabilityLevelHint(
                        Collections.singletonList(
                                new VulnerabilityType("Custom", "UnrestrictedFileUpload")),
                        Messages.getMessage("FILE_UPLOAD_VULNERABLE_DOUBLE_EXTENSION")));
        return AbstractFileUpload.getFileUploadVulnerabilityLevelDefinition(
                LEVEL, "", Variant.UNSECURE, hints);
    }

    @Override
    public boolean validate(FileItem fileItem) throws VulnerableAppException {
        String fileName = fileItem.getName();
        int index = fileName.indexOf(".");
        String extension = fileName.substring(index + 1);
        if (JSP_EXTENSIONS.contains(extension)) {
            return false;
        }
        return super.validate(fileItem);
    }
}
