package image;

import java.awt.*;
import java.io.IOException;

public class Padding {
    private final Color[][] padPixelArray;
    private final int newWidth;
    private final int newHeight;
    private final Image padImage;
    private static final Color WHITE = new Color(255,255,255);


    public Padding(Image image) {

        this.newWidth = closestPowerOfTwo(image.getWidth());
        this.newHeight = closestPowerOfTwo(image.getHeight());
        this.padPixelArray = new Color[newWidth][newHeight];
        this.padImage = image;
    }

    private int closestPowerOfTwo(int n) {
        if (n <= 0) return 1;
        int power = 1;
        while (power < n) {
            power <<= 1;
        }
        return power;
    }

    ///todo- need to check this func works
    public Image paddingTheImage() {
        int originalWidth = padImage.getWidth();
        int originalHeight = padImage.getHeight();

        int paddedWidth = closestPowerOfTwo(originalWidth);
        int paddedHeight = closestPowerOfTwo(originalHeight);


        //todo- this is just checking for the right padding:
        // Print original and new dimensions for debugging
//        System.out.println("Original Width: " + originalWidth + ", Original Height: " + originalHeight);
//        System.out.println("Padded Width: " + paddedWidth + ", Padded Height: " + paddedHeight);
//        System.out.println("Change in Height: " + (paddedHeight - originalHeight));

        int xOffset = (paddedWidth - originalWidth) / 2;
        int yOffset = (paddedHeight - originalHeight) / 2;

        Color[][] newPixelArray = new Color[paddedHeight][paddedWidth];
        // Fill new image with white color
        for (int i = 0; i < paddedHeight; i++) {
            for (int j = 0; j < paddedWidth; j++) {
                newPixelArray[i][j] = WHITE;
            }
        }
        // Copy original image into the center of the new image
        for (int i = 0; i < originalHeight; i++) {
            for (int j = 0; j < originalWidth; j++) {
                newPixelArray[i + yOffset][j + xOffset] = padImage.getPixel(i, j);
            }
        }
        // Create a new Image object with the padded pixel array
        return new Image(newPixelArray, paddedWidth, paddedHeight);
    }


    //todo main func for checks
    //todo main func for checks
    //todo main func for checks
    //todo main func for checks
    //todo main func for checks
    public static void main(String[] args) {
        try {
            // Load the image
            Image image = new Image("examples/new_dingo.png"); // Use your image path here
            Padding padding = new Padding(image);

            // Pad the image
            Image paddedImage = padding.paddingTheImage();

            // Save the padded image
            paddedImage.saveImage("out/padded_new_dingo");
            System.out.println("Padded image saved as out/padded_new_digo.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
