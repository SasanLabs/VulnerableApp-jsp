package org.sasanlabs.vulnerabilities.fileupload.service;

import static org.sasanlabs.framework.VulnerableAppUtility.JSP_EXTENSIONS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean.AttackVectorResponseBean;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean.LevelResponseBean;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;

public class FileUploadLevel4 extends FileUploadLevel2 {

    private static final String LEVEL = "LEVEL_4";

    @Override
    public LevelResponseBean getVulnerabilityLevelDefinition() {
        LevelResponseBean levelResponseBean =
                new LevelResponseBean(
                        LEVEL, "", Arrays.asList(new AttackVectorResponseBean("", "")));
        return levelResponseBean;
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
