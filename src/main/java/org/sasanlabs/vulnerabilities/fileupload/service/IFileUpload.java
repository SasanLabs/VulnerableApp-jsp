package org.sasanlabs.vulnerabilities.fileupload.service;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.vulnerabilities.fileupload.UploadedFileDetails;
/**
 * {@link IFileUpload} represents various FileUpload utilities for uploading files.
 * 
 * General logic is that every utility is vulnerable to one type of UnrestrictedFileUpload
 * and represents one "level".
 * @author preetkaran20@gmail.com KSASAN
 */
public interface IFileUpload {

	static final String WEB_APPS = "webapps";
	static final String STATIC_FILES_FOLDER = "static";
	default File getFileUploadDirectory (String levelDirectory) {
		String tomcatBaseDirectory = System.getProperty("catalina.base");
		File uploadDir = new File(tomcatBaseDirectory + File.separator + WEB_APPS + File.separator
				+ VulnerableAppUtility.SERVLET_CONTEXT + File.separator + STATIC_FILES_FOLDER + File.separator + levelDirectory);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		return uploadDir;
	}
	
	/**
	 * This method will be invoked for uploading the file.
	 * @param fileItem
	 * @return details about the uploaded file.
	 * @throws VulnerableAppException
	 */
	UploadedFileDetails upload(FileItem fileItem) throws VulnerableAppException;
	
	/**
	 * Provides the details about the Vulnerability Level which this fileupload utility represents.
	 * 
	 * @return {@code VulnerabilityDefinitionResponseBean.LevelResponseBean}
	 */
	VulnerabilityDefinitionResponseBean.LevelResponseBean getVulnerabilityLevelResponseBean();
}
