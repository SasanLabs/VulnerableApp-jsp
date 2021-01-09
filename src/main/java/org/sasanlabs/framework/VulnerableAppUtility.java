package org.sasanlabs.framework;

import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@code VulnerableAppUtility} class holds the general constants and utility methods which are used across the VulnerableApp-jsp
 * Application.
 * @author KSASAN preetkaran20@gmail.com
 */
public interface VulnerableAppUtility {
	int DEFAULT_LOAD_ON_STARTUP_VALUE = 10;
	String SERVLET_CONTEXT = "VulnerableApp-jsp";
	
	static String extractVulnerabilityLevel(String pathInfo, Set<String> allowedLevels) throws VulnerableAppException{
		if(pathInfo.startsWith("/")) { 
			pathInfo = pathInfo.substring(1);
		}
		String[] pathInfoParts = pathInfo.startsWith("/") ? pathInfo.substring(1).split("/") :  pathInfo.split("/");
		if(pathInfoParts.length <= 0) {
			throw new VulnerableAppException("No Level Information provided");
		}
		String providedLevel = pathInfoParts[0];
		if(allowedLevels.contains(providedLevel)) {
			return providedLevel;
		} else {
			throw new VulnerableAppException("Provided Level" + providedLevel + " is not valid");
		}
	}
	
	static <T> String serialize(T object) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			//Logger is not integrated. will pick this once integrated.
			return "";
		}
	}
	
}
