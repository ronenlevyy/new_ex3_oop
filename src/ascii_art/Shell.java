package ascii_art;

import ascii_art.exceptions.*;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

import static ascii_art.KeyboardInput.readLine;

/**
 * The main class of the program. It is responsible for handling user input and running the algorithm.
 */
public class Shell {
    private static final String DEFAULT_IMAGE_PATH = "cat.jpeg";
    private static final String DEFAULT_FILE_NAME = "out.html";
    private static final String DEFAULT_FONT = "Courier New";
    private int resolution = DEFAULT_RESOLUTION;
    private Image image;
    private String output = CONSOLE_OUTPUT;
    private ConsoleAsciiOutput consoleAsciiOutput = new ConsoleAsciiOutput();
    private HtmlAsciiOutput htmlAsciiOutput;

    //////////////////////////////////////////
    ////////Ronens changes and pluses/////////
    //////////////////////////////////////////
    private final SubImgCharMatcher asciiCharMatcher;
    private AsciiArtAlgorithm asciiArtAlgorithm;
    //////////////////////////////////////////
    //////////////////////////////////////////
    //////////////////////////////////////////

    // Constants
    private static final String INPUT_PROMPT = ">>> ";
    private static final int DEFAULT_RESOLUTION = 128;
    private static final String CONSOLE_OUTPUT = "console";
    private static final String HTML_OUTPUT = "html";
    private static final String INCORRECT_ADD_FORMAT_MSG = "Did not add due to incorrect format.";
    private static final String INCORRECT_REMOVE_FORMAT_MSG = "Did not remove due to incorrect format.";
    private static final String INCORRECT_RESOLUTION_FORMAT_MSG = "Did not change resolution due to " +
            "incorrect format.";
    private static final String RESOLUTION_SET_MSG = "Resolution set to %d.";

    private static final String INCORRECT_IMAGE_FILE_MSG = "Did not execute due to problem with image " +
            "file.";
    private static final String INCORRECT_IMAGE_FORMAT_MSG = "Did not change image method due to incorrect " +
            "format.";
    private static final String INCORRECT_OUTPUT_FORMAT_MSG = "Did not change output method due to " +
            "incorrect format.";
    private static final String SPACE = " ";
    private static final String CMD_CHARS = "chars";
    private static final String CMD_ADD = "add";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_RES = "res";
    private static final String CMD_IMAGE = "image";
    private static final String CMD_OUTPUT = "output";
    private static final String CMD_ASCII_ART = "asciiArt";
    private static final String CMD_EXIT = "exit";
    private static final String CMD_UP = "up";
    private static final String CMD_DOWN = "down";
    private static final String CMD_ALL = "all";
    private static final String CMD_SPACE = "space";


    /**
     * The constructor of the class. It initializes the CharsBank and the Image.
     *
     * @throws IOException if there is a problem with the image file.
     */
    public Shell() throws IOException {
        try {
            this.image = new Image(DEFAULT_IMAGE_PATH);
        } catch (IOException e) {
            System.out.println(INCORRECT_IMAGE_FILE_MSG);
        }

        //////////////////////////////////////////
        ////////Ronens changes and pluses/////////
        //////////////////////////////////////////
        char[] charSet = {'0','1','2','3','4','5','6','7','8','9'};
        this.asciiCharMatcher = new SubImgCharMatcher(charSet);
        this.asciiArtAlgorithm = new AsciiArtAlgorithm(resolution,image, asciiCharMatcher);
        //////////////////////////////////////////
        //////////////////////////////////////////
        //////////////////////////////////////////
    }

    /**
     * Runs the algorithm.
     */
    public void run() {

        System.out.print(INPUT_PROMPT);
        String input = readLine();

        while (!input.equals(CMD_EXIT)) {
            try {
                String command = input.split(SPACE)[0];
                switch (command) {
                    case CMD_CHARS:
                        this.asciiCharMatcher.printCharSet();
                        break;
                    case CMD_ADD:
                        addChar(input);
                        break;
                    case CMD_REMOVE:
                        removeChar(input);
                        break;
                    case CMD_RES:
                        changeResolution(input);
                        break;
                    case CMD_IMAGE:
                        changeImage(input);
                        break;
                    case CMD_OUTPUT:
                        changeOutput(input);
                        break;
                    case CMD_ASCII_ART:
                        runAlgorithm();
                        break;
                    default:
                        throw new UnknownCommandException();
                }
            } catch (ShellException e) {
                System.out.println(e.getMessage());
            }
            System.out.print(INPUT_PROMPT);
            input = readLine();
        }
        System.exit(0);
    }

    /**
     * The method that runs the algorithm. It creates the ASCII art and outputs it to the console or to an
     * HTML file.
     *
     * @throws SmallCharsetException if the charset is too small.
     */
    private void runAlgorithm() throws SmallCharsetException {
        if (this.asciiCharMatcher.getCharSetSize() < 2) {
            throw new SmallCharsetException();
        }


        //////////////////////////////////////////
        ////////Ronens changes and pluses/////////
        //////////////////////////////////////////
        //        char[][] imageChars = run(this.image, this.resolution, this.charBank);///instead of this run
        char[][] imageChars = asciiArtAlgorithm.run();
        //////////////////////////////////////////
        //////////////////////////////////////////
        //////////////////////////////////////////
//
        if (this.output.equals(CONSOLE_OUTPUT)) {
            consoleAsciiOutput.out(imageChars);
        } else {
            htmlAsciiOutput.out(imageChars);
        }
    }

    /*
     * Changes the output method to either console or html.
     */
    private void changeOutput(String input) throws IncorrectFormatException {
        if (input.length() <= 7) {
            throw new IncorrectFormatException(INCORRECT_OUTPUT_FORMAT_MSG);
        }

        String command = input.substring(7).trim();
        if (command.startsWith(CONSOLE_OUTPUT + SPACE) || command.equals(CONSOLE_OUTPUT)) {
            if (this.output.equals(HTML_OUTPUT)) {
                htmlAsciiOutput = null;
                consoleAsciiOutput = new ConsoleAsciiOutput();
                this.output = CONSOLE_OUTPUT;
            }

        } else if (command.startsWith(HTML_OUTPUT + SPACE) || command.equals(HTML_OUTPUT)) {
            if (this.output.equals(CONSOLE_OUTPUT)) {
                consoleAsciiOutput = null;
                htmlAsciiOutput = new HtmlAsciiOutput(DEFAULT_FILE_NAME, DEFAULT_FONT);
                this.output = HTML_OUTPUT;

            }
        } else {
            throw new IncorrectFormatException(INCORRECT_OUTPUT_FORMAT_MSG);
        }
    }

    /*
     * Changes the image to the one specified in the input.
     */
    private void changeImage(String input) throws IncorrectFormatException {
        if (input.length() <= 6) {
            throw new IncorrectFormatException(INCORRECT_IMAGE_FORMAT_MSG);
        }

        String command = input.substring(6).trim();
        String[] parts = command.split(SPACE);
        if (parts.length == 0) {
            throw new IncorrectFormatException(INCORRECT_IMAGE_FORMAT_MSG);
        }

        String filename = parts[0];

        try {
            this.image = new Image(filename);
            if (this.image.getWidth() < this.resolution) {
                this.resolution = 2;
            }
            /////////////////////////////////////////////
            /////Ronens addes: after a change in image///
            /// the algorithm need to change and have ///
            /////////////// the new image////////////////
            /////////////////////////////////////////////
            this.asciiArtAlgorithm = new AsciiArtAlgorithm(resolution, this.image, this.asciiCharMatcher);
            //////////////////////////////////////////////
            //////////////////////////////////////////////

        } catch (Exception IOException) {
            System.out.println(INCORRECT_IMAGE_FILE_MSG);
        }
    }

    /*
     * Changes the resolution duo to the input.
     */
    private void changeResolution(String input) throws IncorrectFormatException, ResolutionExceedException {
        if (input.equals(CMD_RES)) {
            System.out.printf((RESOLUTION_SET_MSG) + "%n", this.resolution);
        } else if (input.length() <= 4) {
            throw new IncorrectFormatException(INCORRECT_RESOLUTION_FORMAT_MSG);

        } else {

            String command = input.substring(4).trim();
            int minCharsInRow = ImageMinCharsInRow();
            int maxCharsInRow = this.image.getWidth();

            if (command.equals(CMD_UP) || command.startsWith(CMD_UP + SPACE)) {
                if (this.resolution * 2 > maxCharsInRow) {
                    throw new ResolutionExceedException();
                } else {
                    this.resolution *= 2;
                    System.out.printf((RESOLUTION_SET_MSG) + "%n", this.resolution);
                }
            } else if (command.equals(CMD_DOWN) || command.startsWith(CMD_DOWN + SPACE)) {
                if (this.resolution / 2 < minCharsInRow) {
                    throw new ResolutionExceedException();
                } else {
                    this.resolution /= 2;
                    System.out.printf((RESOLUTION_SET_MSG) + "%n", this.resolution);
                }
            } else {
                System.out.printf((RESOLUTION_SET_MSG) + "%n", this.resolution);
            }
        }
        /////////////////////////////////////////////
        /////Ronens addes: after a change in ///////
        //////res the algorithm need to change //////
        ////////// and have the new image////////////////
        /////////////////////////////////////////////
        this.asciiArtAlgorithm = new AsciiArtAlgorithm(resolution, this.image, this.asciiCharMatcher);
        //////////////////////////////////////////////
        //////////////////////////////////////////////

    }

    /*
     * Adds characters to the charset.
     */
    private void addChar(String input) throws IncorrectFormatException {
        if (input.length() <= 4) {
            throw new IncorrectFormatException(INCORRECT_ADD_FORMAT_MSG);
        }
        String command = input.substring(4).trim();

        if (command.startsWith(CMD_ALL)) {
            for (char c = 32; c <= 126; c++) {
                asciiCharMatcher.addChar(c);

            }
        } else if (command.startsWith(CMD_SPACE)) {
            asciiCharMatcher.addChar(' ');

        } else if (command.length() == 1 || command.charAt(1) == ' ') {
            char c = command.charAt(0);
            if (c >= 32 && c <= 126) {
                asciiCharMatcher.addChar(c);

            } else {
                throw new IncorrectFormatException(INCORRECT_ADD_FORMAT_MSG);
            }
        } else if (command.length() == 3 && command.charAt(1) == '-') {
            char start = command.charAt(0);
            char end = command.charAt(2);


            if (start > end) {
                char temp = start;
                start = end;
                end = temp;
            }

            if (start >= 32 && start <= 126 && end >= 32 && end <= 126) {
                for (char c = start; c <= end; c++) {
                    asciiCharMatcher.addChar(c);
                }
            } else {
                throw new IncorrectFormatException(INCORRECT_ADD_FORMAT_MSG);
            }
        } else {
            throw new IncorrectFormatException(INCORRECT_ADD_FORMAT_MSG);
        }


        //todo- after the add we need to update the algorithm---------------------------
        this.asciiArtAlgorithm = new AsciiArtAlgorithm(resolution,image, asciiCharMatcher);
        //todo- after the add we need to update the algorithm---------------------------
    }

    /*
     * Removes characters from the charset.
     */
    private void removeChar(String input) throws IncorrectFormatException {
    if (input.length() <= 7) {
        throw new IncorrectFormatException(INCORRECT_REMOVE_FORMAT_MSG);
    }
    String command = input.substring(7).trim();

    if (command.startsWith(CMD_ALL)) {
        for (char c = 32; c <= 126; c++) {
            asciiCharMatcher.removeChar(c);
        }
    } else if (command.startsWith(CMD_SPACE)) {
        asciiCharMatcher.removeChar(' ');
    } else if (command.length() >= 1 && (command.length() == 1 || command.charAt(1) == ' ')) {
        char c = command.charAt(0);
        if (c >= 32 && c <= 126) {
            asciiCharMatcher.removeChar(c);
        } else {
            throw new IncorrectFormatException(INCORRECT_REMOVE_FORMAT_MSG);
        }
    } else if (command.length() >= 3 && command.charAt(1) == '-') {
        char start = command.charAt(0);
        char end = command.charAt(2);

        if (start > end) {
            char temp = start;
            start = end;
            end = temp;
        }

        if (start >= 32 && start <= 126 && end >= 32 && end <= 126) {
            for (char c = start; c <= end; c++) {
                asciiCharMatcher.removeChar(c);
            }
        } else {
            throw new IncorrectFormatException(INCORRECT_REMOVE_FORMAT_MSG);
        }
    } else {
        throw new IncorrectFormatException(INCORRECT_REMOVE_FORMAT_MSG);
    }

    // Update the algorithm after the remove
    this.asciiArtAlgorithm = new AsciiArtAlgorithm(resolution, image, asciiCharMatcher);
}


    /*
     * Calculates the min chars in a row.
     * @return the min chars in a row.
     */
    private int ImageMinCharsInRow() {
        if (this.image == null || image.getHeight() == 0) {
            return 0;
        }
        return Math.min(1, this.image.getWidth() / this.image.getHeight());
    }

    /**
     * Main method of the algorithm. It creates the ASCII art.
     *
     * @param args the arguments of the program.
     * @throws IOException if there is a problem with the image file.
     */
    public static void main(String[] args) throws IOException {
        Shell shell = new Shell();
        shell.run();
    }
}
