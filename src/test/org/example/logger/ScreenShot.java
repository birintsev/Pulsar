package org.example.logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenShot {
    public void crateScreenshot(String name){
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File("./" + name + ".png"));
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
