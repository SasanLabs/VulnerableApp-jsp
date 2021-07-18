package org.sasanlabs.framework;

import static org.sasanlabs.framework.VulnerableAppUtility.DEFAULT_LOAD_ON_STARTUP_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class {@code VulnerabilitiesInformation} This class provides the details
 * about all the endpoints exposed from the Application.
 */
@WebServlet(value = "/VulnerabilityDefinitions", loadOnStartup = DEFAULT_LOAD_ON_STARTUP_VALUE)
public class VulnerabilitiesInformation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter()
                .append(
                        objectMapper.writeValueAsString(
                                VulnerabilityDefinitionRegistry
                                        .getAllRegisteredVulnerabilityDefinitions()));
        response.setContentType("application/json");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
