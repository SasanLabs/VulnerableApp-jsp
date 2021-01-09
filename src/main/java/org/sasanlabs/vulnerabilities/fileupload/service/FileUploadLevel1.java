package org.sasanlabs.vulnerabilities.fileupload.service;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean.LevelResponseBean;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean.AttackVectorResponseBean;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.vulnerabilities.fileupload.UploadedFileDetails;

public class FileUploadLevel1 implements IFileUpload {

	private static final String LEVEL = "LEVEL_1";

	@Override
	public UploadedFileDetails upload(FileItem fileItem) throws VulnerableAppException {
		String fileName = new File(fileItem.getName()).getName();
		String filePath = this.getFileUploadDirectory(LEVEL).getAbsolutePath() + File.separator + fileName;
		File storeFile = new File(filePath);
		try {
			fileItem.write(storeFile);
			UploadedFileDetails uploadedFileDetails = new UploadedFileDetails(VulnerableAppUtility.SERVLET_CONTEXT
					+ File.separator + "static" + File.separator + LEVEL + File.separator + fileName);
			return uploadedFileDetails;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VulnerableAppException(e);
		}
	}

	@Override
	public LevelResponseBean getVulnerabilityLevelResponseBean() {
		LevelResponseBean levelResponseBean = new LevelResponseBean(LEVEL, "",
				Arrays.asList(new AttackVectorResponseBean("", "")));
		return levelResponseBean;
	}

}
