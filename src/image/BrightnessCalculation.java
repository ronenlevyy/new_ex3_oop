package image;

import java.awt.*;
import java.io.IOException;

public class BrightnessCalculation {

    private final Image subImage;
    private final int MAX_RBG = 255;


    public BrightnessCalculation(Image subImage) {
        this.subImage = subImage;
    }


    public double greyPixelCalculation(Color color) {
        return color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722;
    }

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
        //todo- check you are dividing by the right number
        return totalBrightness / (width * height)/MAX_RBG;
    }




    //todo main func for checks
    //todo main func for checks
    //todo main func for checks
    //todo main func for checks
    //todo main func for checks
    public static void main(String[] args) {
        try {
            // טעינת התמונה
            Image image = new Image("examples/new_dingo.png"); // השתמש במסלול שלך כאן

            // ריפוד התמונה
            Padding padding = new Padding(image);
            Image paddedImage = padding.paddingTheImage();

            // חיתוך התמונה המרופדת לרזולוציה של 4
            Slicing slicing = new Slicing(4, paddedImage);
            Image[][] subImages = slicing.getSubPictures();

            // בדיקת בהירות עבור כל תת-תמונה
            for (int row = 0; row < subImages.length; row++) {
                for (int col = 0; col < subImages[row].length; col++) {
                    BrightnessCalculation brightnessCalculation = new BrightnessCalculation(subImages[row][col]);
                    double brightness = brightnessCalculation.calculateImageBrightness();
                    System.out.println("Brightness of sub-image [" + row + "][" + col + "] is: " + brightness);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
