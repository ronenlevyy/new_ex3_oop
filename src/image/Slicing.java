package image;
import java.awt.*;
import java.io.IOException;


/**
 * A package-private class of the package image.
 * This class provides methods to slice an image into smaller sub-images based on the specified resolution.
 * The sub-images are square and of equal size.
 *
 */
public class Slicing {
    private final int resolution;
    private final Image image;
    private Image[][] subPictures;




    /**
     * Constructs a Slicing object with the given resolution and image.
     * It initializes the sub-images by calling the makeSubPicByRes method.
     *
     * @param resolution the number of sub-images to divide the image into along one dimension
     * @param image the original image to be sliced
     */
    public Slicing(int resolution, Image image) {
        this.resolution = resolution;
        this.image = image;
        this.subPictures = makeSubPicByRes();
    }


    /**
     * Slices the image into smaller sub-images based on the specified resolution.
     * Each sub-image is square and of equal size.
     *
     * @return a 2D array of sub-images
     */
    private Image[][] makeSubPicByRes() {
        int subImageSize = image.getWidth() / resolution; // Size of each sub-image
        int subRow = image.getHeight() / subImageSize;
        int subCol = image.getWidth() / subImageSize;
        this.subPictures = new Image[subRow][subCol];
        for (int row = 0; row < subRow; row++) {
            for (int col = 0; col < subCol; col++) {
                Color[][] subPixels = new Color[subImageSize][subImageSize];
                for (int i = 0; i < subImageSize; i++) {
                    for (int j = 0; j < subImageSize; j++) {
                        int x = col * subImageSize + j;
                        int y = row * subImageSize + i;
                        subPixels[i][j] = image.getPixel(y, x);
                    }
                }
                this.subPictures[row][col] = new Image(subPixels, subImageSize, subImageSize);
            }
        }
        return this.subPictures;
    }


    /**
     * Returns the 2D array of sub-images.
     *
     * @return the 2D array of sub-images
     */
    public Image[][] getSubPictures() {
        return subPictures;
    }

//    public static void main(String[] args) {
//        try {
//            // טעינת התמונה
//            Image image = new Image("examples/new_new_cat.jpeg"); // השתמש במסלול שלך כאן
//
//            // ריפוד התמונה
//            Padding padding = new Padding(image);
//            Image paddedImage = padding.paddingTheImage();
//
//            // חיתוך התמונה המרופדת
//            Slicing slicing = new Slicing(4, paddedImage);
//            Image[][] subImages = slicing.getSubPictures();
//
//            // שמירת כל תת-תמונה
//            int count = 1;
//            for (int row = 0; row < subImages.length; row++) {
//                for (int col = 0; col < subImages[row].length; col++) {
//                    subImages[row][col].saveImage("out/sub_image_" + count);
//                    System.out.println("Sub-image saved as out/sub_image_" + count + ".jpeg");
//                    count++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

