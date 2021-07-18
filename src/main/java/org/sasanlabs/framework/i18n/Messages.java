package org.sasanlabs.framework.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/** @author KSASAN preetkaran20@gmail.com */
public class Messages {
    private static ResourceBundle message =
            ResourceBundle.getBundle(
                    Messages.class.getPackage().getName() + ".messages", Locale.US);

    public static String getMessage(String key) {
        if (key != null && message != null && message.containsKey(key)) {
            return message.getString(key);
        }
        return "";
    }

    public static ResourceBundle getResourceBundle() {
        return message;
    }
}
