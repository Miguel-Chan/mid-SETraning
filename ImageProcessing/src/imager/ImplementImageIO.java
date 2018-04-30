package imager;

import imagereader.IImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;

public class ImplementImageIO implements IImageIO {
    private int[] sourceBuffer;

    private byte[] fileHeader = new byte[FILE_HEADER_SIZE];

    private static final int DOUBLE_WORD_LENGTH = 4;
    private static final int FILE_HEADER_SIZE = 54;
    private static final int PIXEL_SIZE = 3;

    /**
     * @param path the path of the file to be read.
     * @return the image read from the file
     */
    @Override
    public Image myRead(String path) {

        try {
            FileInputStream file = new FileInputStream(path);

            file.read(fileHeader);

            //parse file header to get information about the image
            byte[] widthBytes = new byte[DOUBLE_WORD_LENGTH];
            byte[] heightBytes = new byte[DOUBLE_WORD_LENGTH];
            byte[] sizeBytes = new byte[DOUBLE_WORD_LENGTH];
            for (int i = 0; i < DOUBLE_WORD_LENGTH; i++) {
                widthBytes[i] = fileHeader[18 + i];
                heightBytes[i] = fileHeader[22 + i];
                sizeBytes[i] = fileHeader[34 + i];
            }

            int width = doubleWordToInt(widthBytes);
            int height = doubleWordToInt(heightBytes);
            int size = doubleWordToInt(sizeBytes);

            //read binary data of the image using the info from above
            sourceBuffer = new int[width * height];
            byte[] imageBuffer = new byte[size];
            int skipNum = (size / height - width * 3) % 4;

            file.read(imageBuffer);
            int currentIndex = 0;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    byte[] currentPixel = new byte[PIXEL_SIZE];
                    for (int k = 0; k < PIXEL_SIZE; k++) {
                        currentPixel[k] = imageBuffer[currentIndex + k];
                    }

                    sourceBuffer[(height - i - 1) * width + j] = pixelToInt(currentPixel);
                    currentIndex += 3;
                }
                //have to skip a few bytes due to the image structure.
                currentIndex += skipNum;
            }

            return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(
                    width, height, sourceBuffer, 0, width));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param image The image to be converted
     * @return converted BufferedImage
     */
    public static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage result = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = result.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        return result;
    }

    /**
     * @param image The image to be written to file
     * @param filePath the path of the file to be created from image.
     * @return the input image.
     */
    @Override
    public Image myWrite(Image image, String filePath) {
        try {
            File outputFile = new File(filePath);
            BufferedImage buffer = imageToBufferedImage(image);

            javax.imageio.ImageIO.write(buffer, "bmp", outputFile);

            return image;
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * @param doubleWord The double word to be converted
     * @return the generated int
     */
    public static int doubleWordToInt(byte[] doubleWord) {
        int byte0 = doubleWord[3] & 0xff;
        int byte1 = doubleWord[2] & 0xff;
        int byte2 = doubleWord[1] & 0xff;
        int byte3 = doubleWord[0] & 0xff;

        return byte0 << 24 | byte1 << 16 | byte2 << 8 | byte3;

    }

    /**
     * @param pixel The 3-byte pixel
     * @return The pixel's RGB value in int.
     */
    public static int pixelToInt(byte[] pixel) {
        int byte0 = pixel[2] & 0xff;
        int byte1 = pixel[1] & 0xff;
        int byte2 = pixel[0] & 0xff;

        int firstHalf = 0xff << 24 | byte0 << 16;

        return firstHalf | byte1 << 8 | byte2;
    }

    /**
     * @param word The 2-byte word to be converted
     * @return the converted int
     */
    public static int wordToInt(byte[] word) {
        int byte0 = word[1] & 0xff;
        int byte1 = word[0] & 0xff;

        return byte0 << 8 | byte1;
    }
}
