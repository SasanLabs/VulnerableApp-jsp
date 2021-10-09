package org.sasanlabs.framework.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author KSASAN preetkaran20@gmail.com */
public class Messages {
    private static final Logger LOG = LogManager.getLogger(Messages.class);

    private static ResourceBundle message =
            ResourceBundle.getBundle(
                    Messages.class.getPackage().getName() + ".messages", Locale.US);

    public static String getMessage(String key) {
        if (key != null && message != null && message.containsKey(key)) {
            final String translation = message.getString(key);
            LOG.trace("{} translated as {}", key, translation);
            return translation;
        }
        return "";
    }

    public static ResourceBundle getResourceBundle() {
        return message;
    }
}
