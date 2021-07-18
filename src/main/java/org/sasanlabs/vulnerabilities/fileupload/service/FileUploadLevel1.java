package org.sasanlabs.vulnerabilities.fileupload.service;

import java.util.Arrays;

import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.vulnerableapp.facade.schema.ResourceInformation;
import org.sasanlabs.vulnerableapp.facade.schema.ResourceURI;
import org.sasanlabs.vulnerableapp.facade.schema.Variant;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;

/** @author preetkaran20@gmail.com KSASAN */
public class FileUploadLevel1 extends AbstractFileUpload {

    private static final String LEVEL = "LEVEL_1";

    @Override
    public VulnerabilityLevelDefinition getVulnerabilityLevelDefinition() {
    	VulnerabilityLevelDefinition vulnerabilityLevelDefinition = new VulnerabilityLevelDefinition();
    	vulnerabilityLevelDefinition.setLevel(LEVEL);
    	vulnerabilityLevelDefinition.setDescription("");
    	vulnerabilityLevelDefinition.setVariant(Variant.UNSECURE);
    	ResourceInformation resourceInformation = new ResourceInformation();
    	resourceInformation.setHtmlResource(new ResourceURI(false, ""));
    	vulnerabilityLevelDefinition.setResourceInformation(new ResourceInformation());
    	VulnerabilityLevelDefinition vulnerabilityLevelDefinition =
                new VulnerabilityLevelDefinition(
                        LEVEL, "", Arrays.asList(new AttackVectorResponseBean("", "")));
        return vulnerabilityLevelDefinition;
    }

    @Override
    public boolean validate(FileItem fileItem) throws VulnerableAppException {
        return true;
    }
}
