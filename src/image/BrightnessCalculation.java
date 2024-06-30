package image;

import java.awt.*;


/**
 * This class is responsible for calculating the brightness of a given sub-image.
 * It uses the luminance formula to convert color pixels to grayscale brightness values.
 */
public class BrightnessCalculation {

    private final Image subImage;
    private final int MAX_RBG = 255;


    /**
     * Constructor to initialize the BrightnessCalculation object with a sub-image.
     * @param subImage the sub-image for which brightness will be calculated
     */
    public BrightnessCalculation(Image subImage) {
        this.subImage = subImage;
    }


    /**
     * Calculates the grayscale brightness of a single pixel using the luminance formula.
     * @param color the color object representing the pixel's color
     * @return the grayscale brightness value of the pixel
     */
    public double greyPixelCalculation(Color color) {
        return color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722;
    }



    /**
     * Calculates the average brightness of the entire sub-image.
     * It iterates over all pixels in the sub-image, converts each to grayscale, and computes the average brightness.
     * @return the average brightness of the sub-image as a double
     */
    public double calculateImageBrightness() {
        double totalBrightness = 0;
        int width = subImage.getWidth();
        int height = subImage.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = subImage.getPixel(i, j);
                totalBrightness += greyPixelCalculation(color);
            }
        }
        return totalBrightness / (width * height)/MAX_RBG;
    }
}
