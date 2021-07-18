package org.sasanlabs.vulnerabilities.fileupload.service;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;

/** @author preetkaran20@gmail.com KSASAN */
public class FileUploadLevel3 extends FileUploadLevel2 {

    private static final String LEVEL = "LEVEL_3";
    private static final List<String> UNALLOWED_EXTENSIONS = Arrays.asList("jsp");

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
        return AbstractFileUpload.getFileUploadVulnerabilityLevelDefinition(
                LEVEL, "", Variant.UNSECURE, null);
    }

    @Override
    public boolean validate(FileItem fileItem) throws VulnerableAppException {
        String extension = VulnerableAppUtility.getExtension(fileItem.getName());
        if (UNALLOWED_EXTENSIONS.contains(extension)) {
            return false;
        }
        return super.validate(fileItem);
    }
}
