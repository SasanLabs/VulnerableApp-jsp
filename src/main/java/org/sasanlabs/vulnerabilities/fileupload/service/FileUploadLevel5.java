package org.sasanlabs.vulnerabilities.fileupload.service;

import static org.sasanlabs.framework.VulnerableAppConstants.JSP_EXTENSIONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.framework.i18n.Messages;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelHint;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityType;

public class FileUploadLevel5 extends FileUploadLevel2 {

    private static final String LEVEL = "LEVEL_5";

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
        List<VulnerabilityLevelHint> hints = new ArrayList<VulnerabilityLevelHint>();
        hints.add(
                new VulnerabilityLevelHint(
                        Collections.singletonList(
                                new VulnerabilityType("Custom", "UnrestrictedFileUpload")),
                        Messages.getMessage(
                                "FILE_UPLOAD_IF_NOT_HTML_OR_JSP_OR_JSPX_FILE_EXTENSION")));
        return AbstractFileUpload.getFileUploadVulnerabilityLevelDefinition(
                LEVEL, "", Variant.UNSECURE, hints);
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
