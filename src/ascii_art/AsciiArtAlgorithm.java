package ascii_art;

import image.*;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class provides the algorithm to convert an image into ASCII art based on the specified resolution.
 * It uses caching to optimize the conversion process.
 *
 */
public class AsciiArtAlgorithm {
    private int resolution;
    private Image image;
    private SubImgCharMatcher imageAsciiConvertor;
    private BrightnessCalculation brightnessCalculation;
    private static HashMap<String, char[][]> subImageBrightnessCache;


    /**
     * Constructs an AsciiArtAlgorithm object with the given resolution, image, and character matcher.
     * Initializes the brightness calculation and the cache.
     *
     * @param resolution the number of sub-images to divide the image into along one dimension
     * @param image the original image to be converted to ASCII art
     * @param imageAsciiConvertor the character matcher used to map image brightness to ASCII characters
     */
    public AsciiArtAlgorithm(int resolution, Image image, SubImgCharMatcher imageAsciiConvertor) {
        this.resolution = resolution;
        this.image = image;
        this.imageAsciiConvertor = imageAsciiConvertor;
        this.brightnessCalculation = new BrightnessCalculation(this.image);
        this.subImageBrightnessCache = new HashMap<>();

    }


    /**
     * Generates a unique cache key for the current image and resolution.
     *
     * @return the unique cache key
     */
    private String generateCacheKey() {
        return image.toString() + "_" + resolution;
    }


    /**
     * Converts the image to ASCII art based on the specified resolution.
     * Uses caching to optimize the conversion process.
     *
     * @return a 2D array of ASCII characters representing the image
     */
    public char[][] run() {

        String cacheKey = generateCacheKey();

        // Check if the result is already in the cache
        if (subImageBrightnessCache.containsKey(cacheKey)) {
            return subImageBrightnessCache.get(cacheKey);
        }

        Padding padding = new Padding(image);
        Image paddedImage = padding.paddingTheImage();
        // Slice the padded image into smaller sub-images based on the resolution
        Slicing slicing = new Slicing(resolution, paddedImage);
        Image[][] subImages = slicing.getSubPictures();
        // Initialize the ASCII art character array
        char[][] asciiArt = new char[resolution][subImages[0].length];

        // Convert each sub-image to an ASCII character
        for (int row = 0; row < resolution; row++) {
            for (int col = 0; col < subImages[row].length; col++) {
                brightnessCalculation = new BrightnessCalculation(subImages[row][col]);
                double brightness = brightnessCalculation.calculateImageBrightness();
                asciiArt[row][col] = imageAsciiConvertor.getCharByImageBrightness(brightness);
            }
        }
        // Store the result in the cache
        subImageBrightnessCache.put(cacheKey, asciiArt);
        return asciiArt;
    }


//    public static void main(String[] args) {
//        try {
//            // טעינת התמונה
//            Image image = new Image("examples/cat.jpeg"); // השתמש במסלול שלך כאן
//
//            // ריפוד התמונה
//            Padding padding = new Padding(image);
//            Image paddedImage = padding.paddingTheImage();
//
//            // הגדרת סט התווים
//            char[] charSet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', ';', ':', ',', '.'};
//            SubImgCharMatcher matcher = new SubImgCharMatcher(charSet);
//
//            // יצירת האלגוריתם
//            AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(128, paddedImage, matcher);
//
//            // הרצת האלגוריתם
//            char[][] asciiArt = asciiArtAlgorithm.run();
//
//            // הדפסת התוצאה
//            for (char[] row : asciiArt) {
//                for (char c : row) {
//                    System.out.print(c + " ");
//                }
//                System.out.println();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
