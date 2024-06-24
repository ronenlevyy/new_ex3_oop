package ascii_art.exceptions;

/**
 * An exception class that is thrown when the input format is incorrect.
 */
public class IncorrectFormatException extends ShellException {

    /**
     * Constructs a new IncorrectFormatException with the specified detail message.
     *
     * @param message the detail message.
     */
    public IncorrectFormatException(String message) {
        super(message);
    }
}