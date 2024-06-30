package ascii_art;

import java.util.Scanner;

/**
 * The KeyboardInput class is a singleton class that handles reading input from the keyboard.
 * It ensures only one instance of the Scanner is created and provides a static method to read lines from the keyboard.
 */
class KeyboardInput
{
    private static KeyboardInput keyboardInputObject = null;
    private Scanner scanner;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the Scanner object to read from System.in.
     */
    private KeyboardInput()
    {
        this.scanner = new Scanner(System.in);
    }



    /**
     * Returns the singleton instance of the KeyboardInput class.
     * If the instance does not exist, it creates one.
     *
     * @return the singleton instance of KeyboardInput.
     */
    public static KeyboardInput getObject()
    {
        if(KeyboardInput.keyboardInputObject == null)
        {
            KeyboardInput.keyboardInputObject = new KeyboardInput();
        }
        return KeyboardInput.keyboardInputObject;
    }


    /**
     * Reads a line of input from the keyboard, trims any leading or trailing whitespace, and returns it.
     * This method uses the singleton instance of KeyboardInput.
     *
     * @return a trimmed line of input from the keyboard.
     */
    public static String readLine()
    {
        return KeyboardInput.getObject().scanner.nextLine().trim();
    }
}