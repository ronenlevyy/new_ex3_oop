package image_char_matching;

import ascii_output.AsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.awt.*;
import java.util.*;

//todo- if we add more public func we need to explain in the README file

public class SubImgCharMatcher {


    char[] charSet;
    private HashMap<Character, Double> normBrightnessMap;
    private HashMap<Character, Double> brightnessMap;

    /**
     * Constructor to initialize SubImgCharMatcher with a set of characters.
     * @param charset Array of characters to be used in ASCII art.
     */
    public SubImgCharMatcher(char[] charset){
        this.charSet = charset;
        this.brightnessMap = new HashMap<>();
        for (char c : this.charSet){
            this.brightnessMap.put(c, calculateBrightnessLevels(c));
        }
        // Normalize the brightness values
        linearStretch();
    }


    //todo- this func gets a value of brightness and return the right char from the charSet in the class
    /**
     * Gets the character corresponding to the given brightness value.
     * @param brightness Brightness value of the sub-image.
     * @return Character that best matches the brightness value.
     */
    public char getCharByImageBrightness(double brightness) {
        char bestChar = charSet[0];
        double smallestDifference = Double.MAX_VALUE;

        for (Character c : brightnessMap.keySet()) {
            double difference = Math.abs(brightness - brightnessMap.get(c));
            if (difference < smallestDifference) {
                smallestDifference = difference;
                bestChar = c;
            }
        }
        return bestChar;
    }

     /**
     * Adds a character to the character set and updates the brightness map.
     * @param c Character to be added.
     */
    public void addChar(char c){
        if (brightnessMap.containsKey(c)){
            return;
        }
        char[] newCharSet = new char[charSet.length+1];
        System.arraycopy(charSet, 0, newCharSet, 0, charSet.length);
        newCharSet[charSet.length] = c;
        this.charSet = newCharSet;
        // Sort the charSet in natural (ASCII) order
        Arrays.sort(charSet);

        this.brightnessMap.put(c, calculateBrightnessLevels(c));
        linearStretch();
    }

    /**
     * Removes a character from the character set and updates the brightness map.
     * @param c Character to be removed.
     * @throws NoSuchElementException if the character is not found in the set.
     */
    public void removeChar(char c){
        if (!brightnessMap.containsKey(c)){
            return;
        }
        char[] newCharSet = new char[charSet.length-1];
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

        this.brightnessMap.remove(c);
        linearStretch();
    }

     /*
     * step 1 + 2;
     * Calculates the brightness level of a character.
     * @param theChar Character whose brightness is to be calculated.
     * @return Brightness level of the character.
     */
    private double calculateBrightnessLevels(char theChar) {
        boolean[][] charAsMatrix = CharConverter.convertToBoolArray(theChar);
        double whitePixels = 0;
        for (boolean[] row : charAsMatrix) {
            for (boolean pixel : row){
                if (pixel) {
                    whitePixels++;
                }
            }
        }
        return whitePixels / (charAsMatrix.length*charAsMatrix[0].length);
    }


    /*step 3
     * Performs linear stretching on the brightness values to normalize them.
     */
    private void linearStretch() {
        double minBrightness = Double.MAX_VALUE;
        double maxBrightness = Double.MIN_VALUE;

        for (char brightness : brightnessMap.keySet()) {
            if (brightnessMap.get(brightness) < minBrightness) {
                minBrightness = brightnessMap.get(brightness);
            }
            if (brightnessMap.get(brightness) > maxBrightness) {
                maxBrightness = brightnessMap.get(brightness);
            }
        }

        for (char key : brightnessMap.keySet()) {
            double newBrightness = (brightnessMap.get(key) - minBrightness) / (maxBrightness - minBrightness);
            brightnessMap.put(key, newBrightness);
        }
    }

    //todo- if we add more public func we need to explain in the README file
    //todo- if we add more public func we need to explain in the README file
    //todo- if we add more public func we need to explain in the README file
    //todo- if we add more public func we need to explain in the README file

    /*
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
     * @return Size of the character set.
     */
    public int getCharSetSize() {
        return this.charSet.length;
    }



    public static void main(String[] args) {

        char[] charSet = {'m', 'o'};
        char[] charSet2 = {'A', 'B', 'C', 'D'};
        SubImgCharMatcher macher = new SubImgCharMatcher(charSet2);
        for ( char key : macher.brightnessMap.keySet()){
            System.out.println(macher.brightnessMap.get(key));
        }
        macher.linearStretch();
        for ( char key : macher.brightnessMap.keySet()){
            System.out.println(macher.brightnessMap.get(key));
        }

    }

}
