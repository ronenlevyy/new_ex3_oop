package image;
import java.awt.*;
import java.io.IOException;


//todo problem:
//todo problem:
//todo- the main problem with slicing is that it works well with same size of hight and width
//todo problem:
public class Slicing {
    private final int resolution;
    private final Image image;
    private Image[][] subPictures;


    public Slicing(int resolution, Image image) {
        this.resolution = resolution;
        this.image = image;
        this.subPictures = makeSubPicByRes();
    }


    //todo- check this func is working well
    private Image[][] makeSubPicByRes() {
        int sizeOfSubIm = image.getWidth() / resolution; // גודל תת-תמונה צריך להיות מבוסס על הרוחב והגובה לפי הרזולוציה
        int subRow = resolution;
        int subCol = image.getHeight() / sizeOfSubIm;
//        System.out.println("Image Width: " + image.getWidth() + ", Image Height: " + image.getHeight());
//        System.out.println("Sub-Image Size: " + sizeOfSubIm + ", Rows: " + subRow + ", Columns: " + subCol);

        this.subPictures = new Image[subRow][subCol];

        for (int row = 0; row < subRow; row++) {
            for (int col = 0; col < subCol; col++) {
                Color[][] subPixels = new Color[sizeOfSubIm][sizeOfSubIm];

                for (int i = 0; i < sizeOfSubIm; i++) {
                    for (int j = 0; j < sizeOfSubIm; j++) {
                        int x = col * sizeOfSubIm + j;
                        int y = row * sizeOfSubIm + i;
                        if (x < image.getWidth() && y < image.getHeight()) {
                            subPixels[i][j] = image.getPixel(y, x);
                        } else {
                            subPixels[i][j] = new Color(255, 255, 255); // צבע לבן למילוי
                        }
                    }
                }
                this.subPictures[row][col] = new Image(subPixels, sizeOfSubIm, sizeOfSubIm);
            }
        }
        return this.subPictures;
    }

    public Image[][] getSubPictures() {
        return subPictures;
    }

    public static void main(String[] args) {
        try {
            // טעינת התמונה
            Image image = new Image("examples/dino.png"); // השתמש במסלול שלך כאן

            // ריפוד התמונה
            Padding padding = new Padding(image);
            Image paddedImage = padding.paddingTheImage();

            // חיתוך התמונה המרופדת
            Slicing slicing = new Slicing(4, paddedImage);
            Image[][] subImages = slicing.getSubPictures();

            // שמירת כל תת-תמונה
            int count = 1;
            for (int row = 0; row < subImages.length; row++) {
                for (int col = 0; col < subImages[row].length; col++) {
//                    subImages[row][col].saveImage("out/sub_image_" + count);
//                    System.out.println("Sub-image saved as out/sub_image_" + count + ".jpeg");
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

