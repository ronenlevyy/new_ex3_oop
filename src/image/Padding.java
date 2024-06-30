package image;

import java.awt.*;
import java.io.IOException;


/**
 * A package-private class of the package image.
 * This class provides methods to pad an image to the nearest power of two dimensions.
 * The padded image is centered with a white background.
 *
 */
public class Padding {
    private final Color[][] padPixelArray;
    private final int newWidth;
    private final int newHeight;
    private final Image padImage;
    private static final Color WHITE = new Color(255,255,255);


    /**
     * A package-private class of the package image.
     * This class provides methods to pad an image to the nearest power of two dimensions.
     * The padded image is centered with a white background.
     *
     * @autor Dan Nirel
     */
    public Padding(Image image) {
        this.newWidth = closestPowerOfTwo(image.getWidth());
        this.newHeight = closestPowerOfTwo(image.getHeight());
        this.padPixelArray = new Color[newWidth][newHeight];
        this.padImage = image;
    }


    /**
     * Returns the closest power of two that is greater than or equal to the given number.
     *
     * @param n the number to find the closest power of two for
     * @return the closest power of two that is greater than or equal to the given number
     */
    private int closestPowerOfTwo(int n) {
        if (n <= 0) return 1;
        int power = 1;
        while (power < n) {
            power <<= 1;
        }
        return power;
    }



    /**
     * Pads the image to the nearest power of two dimensions.
     * The padded image is centered with a white background.
     *
     * @return the padded image
     */
    public Image paddingTheImage() {
        int originalWidth = padImage.getWidth();
        int originalHeight = padImage.getHeight();

        int paddedWidth = closestPowerOfTwo(originalWidth);
        int paddedHeight = closestPowerOfTwo(originalHeight);

        int xOffset = (paddedWidth - originalWidth) / 2;
        int yOffset = (paddedHeight - originalHeight) / 2;

        Color[][] newPixelArray = new Color[paddedHeight][paddedWidth];
        for (int i = 0; i < paddedHeight; i++) {
            for (int j = 0; j < paddedWidth; j++) {
                newPixelArray[i][j] = WHITE;
            }
        }
        for (int i = 0; i < originalHeight; i++) {
            for (int j = 0; j < originalWidth; j++) {
                newPixelArray[i + yOffset][j + xOffset] = padImage.getPixel(i, j);
            }
        }
        return new Image(newPixelArray, paddedWidth, paddedHeight);
    }
}
