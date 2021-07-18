package org.sasanlabs.framework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * {@code VulnerableAppUtility} class holds the general constants and utility methods which are used
 * across the VulnerableApp-jsp Application.
 *
 * @author KSASAN preetkaran20@gmail.com
 */
public interface VulnerableAppUtility {
    int DEFAULT_LOAD_ON_STARTUP_VALUE = 10;
    String SERVLET_CONTEXT = "VulnerableApp-jsp";

    static final List<String> JSP_EXTENSIONS = Arrays.asList("jsp", "jspx");

    static String extractVulnerabilityLevel(String pathInfo, Set<String> allowedLevels)
            throws VulnerableAppException {
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        String[] pathInfoParts =
                pathInfo.startsWith("/") ? pathInfo.substring(1).split("/") : pathInfo.split("/");
        if (pathInfoParts.length <= 0) {
            throw new VulnerableAppException("No Level Information provided");
        }
        String providedLevel = pathInfoParts[0];
        if (allowedLevels.contains(providedLevel)) {
            return providedLevel;
        } else {
            throw new VulnerableAppException("Provided Level " + providedLevel + " is not valid");
        }
    }

    static <T> String serialize(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // Logger is not integrated. will pick this once integrated.
            return "";
        }
    }

    /**
     * returns the extension of the provided {@param fileName}. Note: If {@code "."} character is
     * not present in the fileName then it will return null.
     *
     * @param fileName
     * @return extension of the provided {@code fileName} param
     */
    static String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return null;
        }
        String extension = fileName.substring(index + 1).toLowerCase();
        return extension;
    }
}
