package org.sasanlabs.framework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@code VulnerableAppUtility} class holds the general constants and utility methods which are used
 * across the VulnerableApp-jsp Application.
 *
 * @author KSASAN preetkaran20@gmail.com
 */
public interface VulnerableAppUtility {
    Logger LOG = LogManager.getLogger(VulnerableAppUtility.class);

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
            LOG.error("An exception occurred %s. Caused by %s%n%s", e);
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
        return fileName.substring(index + 1).toLowerCase();
    }
}
