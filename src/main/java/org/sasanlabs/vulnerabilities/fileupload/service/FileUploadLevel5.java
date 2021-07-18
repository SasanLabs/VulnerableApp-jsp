package org.sasanlabs.vulnerabilities.fileupload.service;

import static org.sasanlabs.framework.VulnerableAppUtility.JSP_EXTENSIONS;

import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;

public class FileUploadLevel5 extends FileUploadLevel2 {

    private static final String LEVEL = "LEVEL_5";

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
        return AbstractFileUpload.getFileUploadVulnerabilityLevelDefinition(
                LEVEL, "", Variant.UNSECURE, null);
    }

    @Override
    public boolean validate(FileItem fileItem) throws VulnerableAppException {
        String extension = VulnerableAppUtility.getExtension(fileItem.getName());
        if (JSP_EXTENSIONS.contains(extension)) {
            return false;
        } else if (extension == null) {
            /*
             * This usecase is there for many applications which uses split functionality
             * PHP code which is vulnerable to similar attack is
             * {@code strtolower(end(explode('.',$_FILES['image']['name'])));}
             */
            return true;
        }
        return super.validate(fileItem);
    }
}
