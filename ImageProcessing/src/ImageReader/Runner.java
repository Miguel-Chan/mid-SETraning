package ImageReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class Runner {
    public static void run(ImageIO io, ImageProcessor processer) {
        try {
            Image image = io.myRead("assets/1.bmp");
            Image newImg = processer.showGray(image);

            ImageIcon icon = new ImageIcon(image);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(500, 375);
            JLabel label = new JLabel();
            label.setIcon(icon);
            frame.add(label);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            io.myWrite(image, "output/out.bmp");

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
