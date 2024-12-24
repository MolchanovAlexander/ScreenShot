package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Capturer {
    public void captureScreen(String fileName) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point newPoint = new Point(0, 0);
        Rectangle screenRectangle = new Rectangle(newPoint, screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        ImageIO.write(image, "png", new File(fileName));
    }
}
