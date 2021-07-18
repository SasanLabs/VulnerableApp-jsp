package org.sasanlabs.framework;

/**
 * {@link VulnerableAppException} is custom exception for VulnerableApp-jsp
 *
 * @author KSASAN preetkaran20@gmail.com
 */
public class VulnerableAppException extends Exception {

    private static final long serialVersionUID = -5048503582682286844L;

    public VulnerableAppException(String message) {
        super(message);
    }

    public VulnerableAppException(Throwable throwable) {
        super(throwable);
    }

    public VulnerableAppException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
