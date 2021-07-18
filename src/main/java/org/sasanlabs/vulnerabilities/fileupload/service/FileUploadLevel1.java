package org.sasanlabs.vulnerabilities.fileupload.service;

import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;

/** @author preetkaran20@gmail.com KSASAN */
public class FileUploadLevel1 extends AbstractFileUpload {

    private static final String LEVEL = "LEVEL_1";

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
        return AbstractFileUpload.getFileUploadVulnerabilityLevelDefinition(
                LEVEL, "", Variant.UNSECURE, null);
    }

    @Override
    public boolean validate(FileItem fileItem) throws VulnerableAppException {
        return true;
    }
}
