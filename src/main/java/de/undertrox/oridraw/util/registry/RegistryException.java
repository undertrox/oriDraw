package de.undertrox.oridraw.util.registry;

/**
 * This Exception class is used to indicate that an Error occurred with registering something
 */
public class RegistryException extends RuntimeException {
    public RegistryException(String msg) {
        super(msg);
    }
}
