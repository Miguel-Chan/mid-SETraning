package test.imager;

import imager.ImplementImageIO;
import imagereader.IImageIO;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

import static org.junit.Assert.*;

/**
 * ImplementImageIO Tester.
 *
 * @version 1.0
 * @since <pre>Apr.30, 2018</pre>
 */
public class ImplementImageIOTest {

    private IImageIO tester = new ImplementImageIO();

    private static final int TEST_FILE_COUNT = 2;

    /**
     * Method: myRead(String path)
     * Test performs by checking the size of the images produced
     * by myRead and read() from ImageIO, then checking the content
     * of the two images.
     */
    @Test
    public void testMyRead() throws Exception {

        for (int i = 1; i < TEST_FILE_COUNT; i++) {

            String fileName = "assets/test/" + Integer.toString(i) + ".bmp";

            Image myImage = tester.myRead(fileName);
            File inputFile = new File(fileName);
            Image ioImage = javax.imageio.ImageIO.read(inputFile);

            //Test for file size.
            assertEquals(myImage.getHeight(null), ioImage.getHeight(null));
            assertEquals(myImage.getWidth(null), ioImage.getWidth(null));

            //Test for the content of the image.
            int width = ioImage.getWidth(null);
            int height = ioImage.getHeight(null);
            BufferedImage myBuffer = ImplementImageIO.imageToBufferedImage(myImage);
            BufferedImage ioBuffer = ImplementImageIO.imageToBufferedImage(ioImage);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    assertEquals(myBuffer.getRGB(x, y), ioBuffer.getRGB(x, y));
                }
            }
        }
    }


    /**
     * Method: myWrite(Image image, String filePath)
     * Test performs by checking whether the output file has
     * the same MD5 with the original file i.e the output is the
     * same as the input.
     */
    @Test
    public void testMyWrite() throws Exception {
        for (int i = 1; i < TEST_FILE_COUNT; i++) {
            String inputName = "assets/test/" + Integer.toString(i) + ".bmp";

            File in = new File(inputName);
            Image image = javax.imageio.ImageIO.read(in);
            tester.myWrite(image, "assets/testOutput.bmp");

            //getting the MD5 for the two files.
            String originMD5 = getMD5FromFile(inputName);
            String outputMD5 = getMD5FromFile("assets/testOutput.bmp");
            assertEquals(originMD5, outputMD5);

            File output = new File("assets/testOutput.bmp");
            output.delete();
        }
    }


    public static String getMD5FromFile(String path) {
        try {
            byte[] b = Files.readAllBytes(Paths.get(path));
            byte[] hash = MessageDigest.getInstance("MD5").digest(b);
            return new String(hash);
        } catch (Exception e) {
            return null;
        }

    }
}

