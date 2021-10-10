package org.sasanlabs.vulnerabilities.fileupload;

import static org.sasanlabs.framework.VulnerableAppConstants.DEFAULT_LOAD_ON_STARTUP_VALUE;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sasanlabs.framework.VulnerabilityDefinitionRegistry;
import org.sasanlabs.framework.VulnerableAppException;
import org.sasanlabs.framework.VulnerableAppUtility;
import org.sasanlabs.framework.i18n.Messages;
import org.sasanlabs.vulnerabilities.fileupload.service.AbstractFileUpload;
import org.sasanlabs.vulnerabilities.fileupload.service.FileUploadLevel1;
import org.sasanlabs.vulnerabilities.fileupload.service.FileUploadLevel2;
import org.sasanlabs.vulnerabilities.fileupload.service.FileUploadLevel3;
import org.sasanlabs.vulnerabilities.fileupload.service.FileUploadLevel4;
import org.sasanlabs.vulnerabilities.fileupload.service.FileUploadLevel5;
import org.sasanlabs.vulnerabilities.fileupload.service.FileUploadLevel6;
import org.sasanlabs.vulnerabilities.fileupload.service.FileUploadLevelSecure;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityDefinition;
import org.sasanlabs.vulnerableapp.facade.schema.VulnerabilityLevelDefinition;

/**
 * {@code FileUpload} represents the fileupload vulnerability.
 *
 * @author KSASAN preetkaran20@gmail.com
 */
@WebServlet(value = "/FileUpload/*", loadOnStartup = DEFAULT_LOAD_ON_STARTUP_VALUE)
public class FileUpload extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(FileUpload.class);

    private static final long serialVersionUID = 1L;
    private Map<String, AbstractFileUpload> levelVsFileUploadMap = new HashMap<>();
    private static final Set<AbstractFileUpload> FILE_UPLOADS =
            new HashSet<AbstractFileUpload>(
                    Arrays.asList(
                            new FileUploadLevel1(),
                            new FileUploadLevel2(),
                            new FileUploadLevel3(),
                            new FileUploadLevel4(),
                            new FileUploadLevel5(),
                            new FileUploadLevel6(),
                            new FileUploadLevelSecure()));

    private VulnerabilityDefinition vulnerabilityDefinition;

    public void init() throws ServletException {
        Set<VulnerabilityLevelDefinition> vulnerabilityLevelDefinitions = new HashSet<>();
        for (AbstractFileUpload fileUpload : FILE_UPLOADS) {
            vulnerabilityLevelDefinitions.add(fileUpload.getVulnerabilityLevelDefinition());
            levelVsFileUploadMap.put(
                    fileUpload.getVulnerabilityLevelDefinition().getLevel(), fileUpload);
        }
        vulnerabilityDefinition = new VulnerabilityDefinition();
        vulnerabilityDefinition.setId("FileUpload");
        vulnerabilityDefinition.setName(vulnerabilityDefinition.getId());
        vulnerabilityDefinition.setLevelDescriptionSet(vulnerabilityLevelDefinitions);
        vulnerabilityDefinition.setDescription(
                Messages.getMessage("FILE_UPLOAD_VULNERABILITY_DEFINITION"));
        VulnerabilityDefinitionRegistry.add(vulnerabilityDefinition);

        LOG.debug("Servlet initialized");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("GET {}", request.getPathInfo());
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("POST {}", request.getPathInfo());
        try {
            String level =
                    VulnerableAppUtility.extractVulnerabilityLevel(
                            request.getPathInfo(), levelVsFileUploadMap.keySet());
            if (ServletFileUpload.isMultipartContent(request)) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
                ServletFileUpload upload = new ServletFileUpload(factory);

                List<FileItem> formItems;
                formItems = upload.parseRequest(request);

                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (!item.isFormField()) {
                            UploadedFileDetails uploadedFileDetails =
                                    levelVsFileUploadMap.get(level).validateAndUpload(item);
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter()
                                    .append(VulnerableAppUtility.serialize(uploadedFileDetails));
                        }
                    }
                }
            }
        } catch (VulnerableAppException | FileUploadException e) {
            LOG.error("An exception occurred", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().append("Failed to upload file, " + e.getMessage());
        }
    }

    public void destroy() {
        VulnerabilityDefinitionRegistry.remove(vulnerabilityDefinition);
    }
}
