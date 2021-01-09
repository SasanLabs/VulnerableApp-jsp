package org.sasanlabs.framework;

import static org.sasanlabs.framework.VulnerableAppUtility.DEFAULT_LOAD_ON_STARTUP_VALUE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class {@code VulnerabilitiesInformation}
 * This class provides the details about all the endpoints exposed from the Application.
 */
@WebServlet(value = "/VulnerabilityDefinitions", loadOnStartup = DEFAULT_LOAD_ON_STARTUP_VALUE)
public class VulnerabilitiesInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;

/*
	{
		   "Name":"UnrestrictedFileUpload",
		   "Description":"Uploaded files represent a significant risk to applications. The first step in many attacks is to get some code to the system to be attacked. Then the attack only needs to find a way to get the code executed.<br/>The consequences of unrestricted file upload can vary, including complete system takeover, an overloaded file system or database, forwarding attacks to back-end systems, client-side attacks, or simple defacement. It depends on what the application does with the uploaded file and especially where it is stored.<br/><br/>Important Links:<br/><ol> <li> <a href=\"https://owasp.org/www-community/vulnerabilities/Unrestricted_File_Upload\">Owasp Wiki Link</a>  <li> <a href=\"https://www.youtube.com/watch?v=CmF9sEyKZNo\">Ebrahim Hegazy talk on Unrestricted File Uploads</a> <li> <a href=\"https://www.sans.org/blog/8-basic-rules-to-implement-secure-file-uploads/\">Sans rules to implement secure file uploads</a> </ol>",
		   "Detailed Information":[
		      {
		         "Level":"LEVEL_1",
		         "HtmlTemplate":"LEVEL_1/FileUpload",
		         "AttackVectors":[
		            {
		               "CurlPayload":"Payload is not applicable for the attack vector.",
		               "Description":"There is no validation on uploaded file's name."
		            }
		         ]
		      }
		   ]
		}
*/		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().append(objectMapper.writeValueAsString(VulnerabilityInformationRegistry.getAllRegisteredVulnerabilityDefinitionResponseBeans()));
		response.setContentType("application/json");
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
