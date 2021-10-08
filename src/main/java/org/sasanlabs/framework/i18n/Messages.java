package org.sasanlabs.framework.i18n;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author KSASAN preetkaran20@gmail.com
 */
public class Messages {
    private static final Logger LOG = LogManager.getLogger(Messages.class);

    private static ResourceBundle message =
            ResourceBundle.getBundle(
                    Messages.class.getPackage().getName() + ".messages", Locale.US);

    public static String getMessage(String key) {
        if (key != null && message != null && message.containsKey(key)) {
            final String translation = message.getString(key);
            LOG.trace(() -> String.format("%s translated as %s", key, translation));
            return translation;
        }
        return "";
    }

    public static ResourceBundle getResourceBundle() {
        return message;
    }
}
