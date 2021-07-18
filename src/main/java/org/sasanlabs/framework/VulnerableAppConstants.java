package org.sasanlabs.framework;

import java.util.Arrays;
import java.util.List;

/**
 * Constants for the Application
 *
 * @author preetkaran20@gmail.com KSASAN
 */
public interface VulnerableAppConstants {
    static int DEFAULT_LOAD_ON_STARTUP_VALUE = 10;
    static String SERVLET_CONTEXT = "VulnerableApp-jsp";
    static String SLASH = "/";
    static final List<String> JSP_EXTENSIONS = Arrays.asList("jsp", "jspx");
}
