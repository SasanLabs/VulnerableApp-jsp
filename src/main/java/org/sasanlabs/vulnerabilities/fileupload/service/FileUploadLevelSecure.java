package org.sasanlabs.vulnerabilities.fileupload.service;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean.AttackVectorResponseBean;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean.LevelResponseBean;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;

public class FileUploadLevelSecure extends AbstractFileUpload {

    private static final String LEVEL = "LEVEL_6";
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpeg");

    @Override
    public LevelResponseBean getVulnerabilityLevelDefinition() {
        LevelResponseBean levelResponseBean =
                new LevelResponseBean(
                        LEVEL, "", Arrays.asList(new AttackVectorResponseBean("", "")));
        return levelResponseBean;
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
