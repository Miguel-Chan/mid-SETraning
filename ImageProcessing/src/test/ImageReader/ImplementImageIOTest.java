package test.ImageReader; 

import ImageReader.ImageIO;
import ImageReader.ImplementImageIO;
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
* @since <pre>Apr.30, 2018</pre>
* @version 1.0 
*/ 
public class ImplementImageIOTest {

    ImageIO tester = new ImplementImageIO();

    /**
     * Method: myRead(String path)
     * Test performs by checking the size of the images produced
     * by myRead and read() from ImageIO, then checking the content
     * of the two images.
     */
    @Test
    public void testMyRead() throws Exception {

        Image myImage = tester.myRead("assets/2.bmp");
        File inputFile = new File("assets/2.bmp");
        Image ioImage = javax.imageio.ImageIO.read(inputFile);

        //Test for file size.
        assertEquals(myImage.getHeight(null), ioImage.getHeight(null));
        assertEquals(myImage.getWidth(null), ioImage.getWidth(null));

        //Test for the content of the image.
        int width = ioImage.getWidth(null);
        int height = ioImage.getHeight(null);
        BufferedImage myBuffer = ImplementImageIO.ImageToBufferedImage(myImage);
        BufferedImage ioBuffer = ImplementImageIO.ImageToBufferedImage(ioImage);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                assertEquals(myBuffer.getRGB(x, y), ioBuffer.getRGB(x, y));
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
        File in = new File("assets/3.bmp");
        Image image = javax.imageio.ImageIO.read(in);
        tester.myWrite(image, "output/test3.bmp");

        //getting the MD5 for the two files.
        String originMD5 = getMD5FromFile("assets/3.bmp");
        String outputMD5 = getMD5FromFile("output/test3.bmp");
        assertEquals(originMD5, outputMD5);
    }


    public static String getMD5FromFile(String path) {
        try {
            byte[] b = Files.readAllBytes(Paths.get(path));
            byte[] hash = MessageDigest.getInstance("MD5").digest(b);
            return new String(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

