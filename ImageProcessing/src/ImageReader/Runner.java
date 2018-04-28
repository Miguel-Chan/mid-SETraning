package ImageReader;

import javax.swing.*;
import java.awt.*;

public class Runner {
    public static void run(ImplementImageIO io, ImplementImageProcessor processer) {
        try {
            Image image = io.myRead("1.bmp");

            ImageIcon icon = new ImageIcon(image);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(500, 375);
            JLabel label = new JLabel();
            label.setIcon(icon);
            frame.add(label);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
