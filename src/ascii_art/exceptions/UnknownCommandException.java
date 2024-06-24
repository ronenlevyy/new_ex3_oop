package ascii_art.exceptions;

/**
 * An exception class that is thrown when the command is unknown.
 */
public class UnknownCommandException extends ShellException {
    private static final String UNKNOWN_COMMAND_MSG = "Did not execute due to incorrect command.";

    /**
     * Constructs a new UnknownCommandException with the specified detail message.
     */
    public UnknownCommandException() {
        super(UNKNOWN_COMMAND_MSG);
    }
}
