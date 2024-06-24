package ascii_art.exceptions;

/**
 * An exception class that is thrown when the resolution exceeds the boundaries.
 */
public class ResolutionExceedException extends ShellException {
    private static final String RESOLUTION_EXCEED_MSG = "Did not change resolution due to exceeding " +
            "boundaries.";

    /**
     * Constructs a new ResolutionExceedException with the specified detail message.
     */
    public ResolutionExceedException() {
        super(RESOLUTION_EXCEED_MSG);
    }
}