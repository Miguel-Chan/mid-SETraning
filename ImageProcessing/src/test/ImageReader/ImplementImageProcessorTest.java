package test.ImageReader;

import ImageReader.ImageIO;
import ImageReader.ImageProcessor;
import ImageReader.ImplementImageIO;
import ImageReader.ImplementImageProcessor;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * ImplementImageProcessor Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>APR.30, 2018</pre>
 */
public class ImplementImageProcessorTest {

    private static final int TEST_SIZE = 6;

    /**
     * Method: showChanelR(Image sourceImage)
     * Test performs by checking whether the output image is the
     * same as the prepared output sample
     */
    ImageProcessor tester = new ImplementImageProcessor();
    @Test
    public void testShowChanelR() throws Exception {
        test("red");
    }


    /**
     * @param color : color filter to be test
     * @throws Exception
     * test by by checking whether the output image is the
     * same as the prepared output sample
     */
    private void test(String color) throws Exception{
        ImageIO io = new ImplementImageIO();
        for (int i = 1; i <= TEST_SIZE; i++) {
            String fileName = "assets/" + Integer.toString(i) + ".bmp";
            String testOutputSampleFileName = "assets/" + Integer.toString(i)
                    + "-" + color + ".bmp";

            Image origin = io.myRead(fileName);
            Image expecting = io.myRead(testOutputSampleFileName);
            Image output;
            switch (color) {
                case "red":
                    output = tester.showChanelR(origin);
                    break;
                case "blue":
                    output = tester.showChanelB(origin);
                    break;
                case "green":
                    output = tester.showChanelG(origin);
                    break;
                case "gray":
                    output = tester.showGray(origin);
                    break;
                default:
                    throw new Exception("Parameter color is invalid");
            }

            assertEquals(output.getHeight(null), expecting.getHeight(null));
            assertEquals(output.getWidth(null), expecting.getWidth(null));

            //Test for the content of the image.
            int width = expecting.getWidth(null);
            int height = expecting.getHeight(null);
            BufferedImage myBuffer = ImplementImageIO.ImageToBufferedImage(output);
            BufferedImage expectingBuffer = ImplementImageIO.ImageToBufferedImage(expecting);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    assertEquals(myBuffer.getRGB(x, y), expectingBuffer.getRGB(x, y));
                }
            }
        }
    }

    /**
     * Method: showChanelG(Image sourceImage)
     */
    @Test
    public void testShowChanelG() throws Exception {
        test("green");
    }

    /**
     * Method: showChanelB(Image sourceImage)
     */
    @Test
    public void testShowChanelB() throws Exception {
        test("blue");
    }

    /**
     * Method: showGray(Image sourceImage)
     */
    @Test
    public void testShowGray() throws Exception {
        test("gray");
    }


} 
