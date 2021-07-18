package org.sasanlabs.vulnerabilities.fileupload.service;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean.AttackVectorResponseBean;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean.LevelResponseBean;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;

/** @author preetkaran20@gmail.com KSASAN */
public class FileUploadLevel2 extends AbstractFileUpload {

    private static final String LEVEL = "LEVEL_2";
    private static final List<String> UNALLOWED_EXTENSIONS =
            Arrays.asList("html", "htm", "svg", "dhtml", "shtml", "xhtml", "xml", "svgz");

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
        if (extension == null) {
            return false;
        }
        return !UNALLOWED_EXTENSIONS.contains(extension);
    }
}
