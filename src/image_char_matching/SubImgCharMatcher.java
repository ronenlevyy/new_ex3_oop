package image_char_matching;

import ascii_output.AsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.awt.*;
import java.util.*;

/**
 * This class provides the algorithm to convert an image into ASCII art based on the specified resolution and
 * image.
 */
public class SubImgCharMatcher {


    char[] charSet;
    private HashMap<Character, Double> rawBrightnessCache; // Stores raw brightness values
    private HashMap<Double, Set<Character>> charSetNormCache; // Stores normalized brightness values and
    // corresponding set of characters
    private HashMap<Character, Double> normBrightnessCache; // Stores normalized brightness values and
    // corresponding characters

    private double minBrightness = Double.MAX_VALUE;
    private double maxBrightness = Double.MIN_VALUE;

    /**
     * Constructor to initialize SubImgCharMatcher with a set of characters.
     *
     * @param charset Array of characters to be used in ASCII art.
     */
    public SubImgCharMatcher(char[] charset) {
        this.charSet = charset;
        this.rawBrightnessCache = new HashMap<>();
        this.charSetNormCache = new HashMap<>();
        this.normBrightnessCache = new HashMap<>();

        for (char c : this.charSet) {
            calculateBrightnessLevels(c);
        }
        // Normalize the brightness values
        linearStretch();
        rebuildCharSetNormCache();
    }

    /**
     * Gets the character corresponding to the given brightness value.
     *
     * @param brightness Brightness value of the sub-image.
     * @return Character that best matches the brightness value.
     */
    public char getCharByImageBrightness(double brightness) {
        char bestChar = charSet[0];
        double smallestDifference = Double.MAX_VALUE;

        for (Character c : normBrightnessCache.keySet()) {
            double difference = Math.abs(brightness - normBrightnessCache.get(c));
            if (difference < smallestDifference) {
                smallestDifference = difference;
                bestChar = c;
            }
        }
        return bestChar;
    }

    /**
     * Adds a character to the character set and updates the brightness map.
     *
     * @param c Character to be added.
     */
    public void addChar(char c) {
        if (!rawBrightnessCache.containsKey(c)) {
            char[] newCharSet = new char[charSet.length + 1];
            System.arraycopy(charSet, 0, newCharSet, 0, charSet.length);
            newCharSet[charSet.length] = c;
            this.charSet = newCharSet;

            // Sort the charSet in natural (ASCII) order
            Arrays.sort(charSet);

            calculateBrightnessLevels(c);
            linearStretch();
            rebuildCharSetNormCache();
        }
    }

    /**
     * Removes a character from the character set and updates the brightness map.
     *
     * @param c Character to be removed.
     * @throws NoSuchElementException if the character is not found in the set.
     */
    public void removeChar(char c) {
        if (rawBrightnessCache.containsKey(c)) {
            char[] newCharSet = new char[charSet.length - 1];
            int index = 0;
            for (char value : charSet) {
                if (value == c) {
                    continue;
                }
                newCharSet[index] = value;
                index++;
            }
            this.charSet = newCharSet;

            // Sort the charSet in natural (ASCII) order
            Arrays.sort(charSet);

            this.rawBrightnessCache.remove(c);
            this.normBrightnessCache.remove(c);

            linearStretch();
            rebuildCharSetNormCache();
        }
    }

    /*
     * Rebuilds the charSetNormCache after changes to the character set.
     */
    private void rebuildCharSetNormCache() {
        this.charSetNormCache = new HashMap<>();

        for (char c : charSet) {
            double brightness = normBrightnessCache.get(c);
            Set<Character> charSet = charSetNormCache.getOrDefault(brightness, new HashSet<>());
            charSet.add(c);
            charSetNormCache.put(brightness, charSet);
        }
    }

    /*
     * Calculates the brightness level of a character.
     *
     * @param theChar Character whose brightness is to be calculated.
     */
    private void calculateBrightnessLevels(char theChar) {
        if (!this.rawBrightnessCache.containsKey(theChar)) {
            boolean[][] charAsMatrix = CharConverter.convertToBoolArray(theChar);
            double whitePixels = 0;
            for (boolean[] row : charAsMatrix) {
                for (boolean pixel : row) {
                    if (pixel) {
                        whitePixels++;
                    }
                }
            }
            double rawBrightness = whitePixels / (charAsMatrix.length * charAsMatrix[0].length);
            rawBrightnessCache.put(theChar, rawBrightness);
        }
        normBrightnessCache.put(theChar, rawBrightnessCache.get(theChar));
    }

    /*
     * Updates the minimum and maximum brightness values.
     */
    private void updateMinMax() {
        minBrightness = Double.MAX_VALUE;
        maxBrightness = Double.MIN_VALUE;

        // Find the minimum and maximum brightness values
        for (double brightness : rawBrightnessCache.values()) {
            if (brightness < minBrightness) {
                minBrightness = brightness;
            }
            if (brightness > maxBrightness) {
                maxBrightness = brightness;
            }
        }
    }

    /**
     * Performs linear stretching on the brightness values to normalize them.
     */
    private void linearStretch() {
        updateMinMax();

        // Normalize the brightness values and put them into normBrightnessCache
        for (char c : charSet) {
            double charRawBrightness = rawBrightnessCache.get(c);
            double newBrightness = (charRawBrightness - minBrightness) / (maxBrightness - minBrightness);
            normBrightnessCache.put(c, newBrightness);
        }

        // Rebuild charSetNormCache
        rebuildCharSetNormCache();
    }

    /**
     * Prints the character set in a sorted order.
     */
    public void printCharSet() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < charSet.length; i++) {
            result.append(charSet[i]);
            if (i < charSet.length - 1) {
                result.append(' ');
            }
        }
        System.out.println(result.toString());
    }

    /**
     * Gets the size of the character set.
     *
     * @return Size of the character set.
     */
    public int getCharSetSize() {
        return this.charSet.length;
    }

}
