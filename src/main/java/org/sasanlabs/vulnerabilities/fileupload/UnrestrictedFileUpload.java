package org.sasanlabs.vulnerabilities.fileupload;

import static org.sasanlabs.framework.VulnerableAppUtility.DEFAULT_LOAD_ON_STARTUP_VALUE;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.sasanlabs.framework.VulnerabilityDefinitionResponseBean;
import org.sasanlabs.framework.VulnerabilityInformationRegistry;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.vulnerabilities.fileupload.service.FileUploadLevel1;
import org.sasanlabs.vulnerabilities.fileupload.service.IFileUpload;

/**
 * {@code UnrestrictedFileUpload} represents the fileupload vulnerability.
 * 
 * @author KSASAN preetkaran20@gmail.com
 */
@WebServlet(value = "/UnrestrictedFileUpload/*", loadOnStartup = DEFAULT_LOAD_ON_STARTUP_VALUE)
public class UnrestrictedFileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, IFileUpload> levelVsFileUploadMap = new HashMap<>();
	private static final Set<IFileUpload> FILE_UPLOADS = new HashSet<IFileUpload>(Arrays.asList(new FileUploadLevel1()));

	private VulnerabilityDefinitionResponseBean vulnerabilityDefinitionResponseBean;

	
	public void init() throws ServletException {
		Set<VulnerabilityDefinitionResponseBean.LevelResponseBean> levelResponseBeans = new HashSet<>();
		for(IFileUpload fileUpload : FILE_UPLOADS) {
			levelResponseBeans.add(fileUpload.getVulnerabilityLevelResponseBean());
			levelVsFileUploadMap.put(fileUpload.getVulnerabilityLevelResponseBean().getLevel(), fileUpload);
		}
		vulnerabilityDefinitionResponseBean = new VulnerabilityDefinitionResponseBean("UnrestrictedFileUpload", "",levelResponseBeans);
		VulnerabilityInformationRegistry.add(vulnerabilityDefinitionResponseBean);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String level = VulnerableAppUtility.extractVulnerabilityLevel(request.getPathInfo(), levelVsFileUploadMap.keySet());
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
				ServletFileUpload upload = new ServletFileUpload(factory);
				
				List<FileItem> formItems;
				formItems = upload.parseRequest(request);

				if (formItems != null && formItems.size() > 0) {
					for (FileItem item : formItems) {
						if(!item.isFormField()) {
							UploadedFileDetails uploadedFileDetails = levelVsFileUploadMap.get(level).upload(item);
							response.setStatus(HttpServletResponse.SC_ACCEPTED);
							response.getWriter().append(VulnerableAppUtility.serialize(uploadedFileDetails));
						}
					}
				}
			}
		} catch (VulnerableAppException | FileUploadException e) {
			//For now as logger is not integrated
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().append("Failed to upload file, " + e.getMessage());
		}
	}

	public void destroy() {
		VulnerabilityInformationRegistry.remove(vulnerabilityDefinitionResponseBean);
	}

}
