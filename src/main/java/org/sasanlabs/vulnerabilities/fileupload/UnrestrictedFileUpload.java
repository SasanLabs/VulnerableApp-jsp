package org.sasanlabs.vulnerabilities.fileupload;

import static org.sasanlabs.framework.Constants.DEFAULT_LOAD_ON_STARTUP_VALUE;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.sasanlabs.framework.AllEndPointResponseBean;
import org.sasanlabs.framework.VulnerabilityInformationRegistry;

/**
 * Servlet implementation class UnrestrictedFileUpload
 * 
 * @author KSASAN preetkaran20@gmail.com
 */
@WebServlet(value = "/UnrestrictedFileUpload/*", loadOnStartup = DEFAULT_LOAD_ON_STARTUP_VALUE)
public class UnrestrictedFileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String WEB_APPS = "webapps";
	private static final String STATIC_FILES_FOLDER = "static";
	private String tomcatBaseDirectory;

	private AllEndPointResponseBean allEndPointResponseBean = new AllEndPointResponseBean("UnrestrictedFileUpload", "",
			Arrays.asList(new AllEndPointResponseBean.LevelResponseBean("LEVEL_1", "",
					Arrays.asList(new AllEndPointResponseBean.AttackVectorResponseBean("", "")))));

	public void init() throws ServletException {
		VulnerabilityInformationRegistry.add(allEndPointResponseBean);
		this.tomcatBaseDirectory = System.getProperty("catalina.base");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			ServletFileUpload upload = new ServletFileUpload(factory);
			File uploadDir = new File(this.tomcatBaseDirectory + File.separator + WEB_APPS + File.separator
					+ this.getServletContext().getContextPath() + File.separator + STATIC_FILES_FOLDER);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			List<FileItem> formItems;
			try {
				formItems = upload.parseRequest(request);

				if (formItems != null && formItems.size() > 0) {
					for (FileItem item : formItems) {
						if (!item.isFormField()) {
							String fileName = new File(item.getName()).getName();
							String filePath = uploadDir.getAbsolutePath() + File.separator + fileName;
							File storeFile = new File(filePath);
							item.write(storeFile);
							response.getWriter().append("File " + STATIC_FILES_FOLDER + File.separator + fileName
									+ " has uploaded successfully!");
						}
					}
				}
			} catch (Exception e) {
				throw new IOException(e);
			}

		}
	}

	public void destroy() {
		VulnerabilityInformationRegistry.remove(allEndPointResponseBean);
	}

}
