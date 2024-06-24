package ascii_art.exceptions;

/**
 * An exception class that is thrown when the charset is too small.
 */
public class SmallCharsetException extends ShellException {
    private static final String SMALL_CHARSET_MSG = "Did not execute. Charset is too small.";

    /**
     * Constructs a new SmallCharsetException with the specified detail message.
     */
    public SmallCharsetException() {
        super(SMALL_CHARSET_MSG);
    }
}