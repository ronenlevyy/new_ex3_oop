package ascii_art.exceptions;

/**
 * An exception class that is thrown when an error occurs in the shell.
 */
public class ShellException extends Exception {

    /**
     * Constructs a new ShellException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ShellException(String message) {
        super(message);
    }
}
