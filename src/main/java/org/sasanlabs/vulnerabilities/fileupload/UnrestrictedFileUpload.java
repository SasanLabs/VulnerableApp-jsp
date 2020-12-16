package org.sasanlabs.vulnerabilities.fileupload;

import static org.sasanlabs.framework.Constants.DEFAULT_LOAD_ON_STARTUP_VALUE;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
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
 * https://stackoverflow.com/questions/1812244/simplest-way-to-serve-static-data-from-outside-the-application-server-in-a-java
 */
@WebServlet(value = "/UnrestrictedFileUpload", loadOnStartup = DEFAULT_LOAD_ON_STARTUP_VALUE)
public class UnrestrictedFileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AllEndPointResponseBean allEndPointResponseBean = new AllEndPointResponseBean("UnrestrictedFileUpload", "",
			Arrays.asList(new AllEndPointResponseBean.LevelResponseBean("LEVEL_1", "",
					Arrays.asList(new AllEndPointResponseBean.AttackVectorResponseBean("", "")))));

	public void init() throws ServletException {
		VulnerabilityInformationRegistry.add(allEndPointResponseBean);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(request)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			ServletFileUpload upload = new ServletFileUpload(factory);
			ServletContext context = getServletContext();
			URL resourceUrl = context.getResource("/");
			File uploadDir = new File(resourceUrl.toString());
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				List<FileItem> formItems = upload.parseRequest(request);
				if (formItems != null && formItems.size() > 0) {
					for (FileItem item : formItems) {
						if (!item.isFormField()) {
							String fileName = new File(item.getName()).getName();
							String filePath = resourceUrl.toString() + File.separator + fileName;
							File storeFile = new File(filePath);
							item.write(storeFile);
							response.getWriter().append("File " + filePath + " has uploaded successfully!");
						}
					}
				}
			} catch (Exception ex) {
				throw new ServletException(ex);
			}
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		VulnerabilityInformationRegistry.remove(allEndPointResponseBean);
	}

}
