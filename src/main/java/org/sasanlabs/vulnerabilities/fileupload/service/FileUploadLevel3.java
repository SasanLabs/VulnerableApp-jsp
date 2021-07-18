package org.sasanlabs.vulnerabilities.fileupload.service;

import static org.sasanlabs.framework.VulnerableAppConstants.JSP_EXTENSIONS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.framework.i18n.Messages;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelHint;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityType;

public class FileUploadLevel3 extends FileUploadLevel2 {

    private static final String LEVEL = "LEVEL_3";

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
        List<VulnerabilityLevelHint> hints = new ArrayList<VulnerabilityLevelHint>();
        hints.add(
                new VulnerabilityLevelHint(
                        Collections.singletonList(
                                new VulnerabilityType("Custom", "UnrestrictedFileUpload")),
                        Messages.getMessage(
                                "FILE_UPLOAD_IF_NOT_HTML_FILE_EXTENSION_AND_DOESN'T_CONTAINS_SCRIPTLET")));
        return AbstractFileUpload.getFileUploadVulnerabilityLevelDefinition(
                LEVEL, "", Variant.UNSECURE, hints);
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
