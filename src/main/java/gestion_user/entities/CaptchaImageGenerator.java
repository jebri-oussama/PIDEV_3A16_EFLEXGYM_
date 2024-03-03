package gestion_user.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaptchaImageGenerator {
    public static BufferedImage generateCaptchaImage(String code) {
        int width = 150;
        int height = 50;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Set background color
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        // Set font and draw code
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.ORANGE);
        g.drawString(code, 20, 30);


        g.dispose();
        return image;
    }
}
