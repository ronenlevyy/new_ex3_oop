package image_char_matching;

import ascii_output.AsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.awt.*;
import java.util.*;

//todo- if we add more public func we need to explain in the README file

public class SubImgCharMatcher {


    char[] charSet;
    private HashMap<Character, Double> brightnessMap;

    /**
     *  ///todo- might be wrong;
     * @param charset
     */
    public SubImgCharMatcher(char[] charset){
        this.charSet = charset;
        this.brightnessMap = new HashMap<>();
        for (char c : this.charSet){
            this.brightnessMap.put(c, calculateBrightnessLevels(c));
        }
        linearStretch();
    }


    //todo- this func gets a value of brightness and return the right char from the charSet in the class
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

    //todo- this func add c to the chrSet
    public void addChar(char c){
        if (brightnessMap.containsKey(c)){
            return;
        }
        char[] newCharSet = new char[charSet.length+1];
        System.arraycopy(charSet, 0, newCharSet, 0, charSet.length);
        newCharSet[charSet.length] = c;
        this.charSet = newCharSet;
        this.brightnessMap.put(c, calculateBrightnessLevels(c));
        linearStretch();
    }

    //todo- this func remove c from the charSet
    public void removeChar(char c){
        //todo- add an exception if the char is not found
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
        this.brightnessMap.remove(c);
        linearStretch();
    }



    /**
     * step 1 + 2;
     * @param theCar
     * @return
     */
    private double calculateBrightnessLevels(char theCar) {
        boolean[][] charAsMatrix = CharConverter.convertToBoolArray(theCar);
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


    /**step 3
     *
     * @param
     * @return
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
            brightnessMap.replace(key, newBrightness);
        }
    }

    //todo- if we add more public func we need to explain in the README file
    //todo- if we add more public func we need to explain in the README file
    //todo- if we add more public func we need to explain in the README file
    //todo- if we add more public func we need to explain in the README file

     public void printCharSet() {
        // Sort the charSet in natural (ASCII) order
        Arrays.sort(charSet);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < charSet.length; i++) {
            result.append(charSet[i]);
            if (i < charSet.length - 1) {
                result.append(' ');
            }
        }

        System.out.println(result.toString());
    }

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
