package cosc202.andie.test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ContrastBrightnessAdjust;


import java.awt.Color;
import java.awt.image.BufferedImage;

public class ContrastBrightnessAdjustTest {

    @Test
    void DefaultContrastAndBrightness() {
        ContrastBrightnessAdjust c = new ContrastBrightnessAdjust(0, 0);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        Color testColor = new Color(100, 100, 100);
        image.setRGB(0, 0, testColor.getRGB());
        int x = image.getRGB(0, 0);

        BufferedImage adjusted = c.apply(image);
        int y = adjusted.getRGB(0, 0);
        Assertions.assertEquals(x, y);

    }

    @Test
    void contrastAdjustTest() {
        ContrastBrightnessAdjust c = new ContrastBrightnessAdjust(30, 0);

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Color testColor = new Color(100, 100, 100);
        image.setRGB(0, 0, testColor.getRGB());
        int argb = image.getRGB(0, 0);

        int a = (argb & 0xFF000000);
        int r = (argb & 0x00FF0000) >> 16;
        int g = (argb & 0x0000FF00) >> 8;
        int b = (argb & 0x000000FF);

        int r2 = (int) ((1 + 30.0 / 100) * (r - 127.5) + 127.5 * (1 + 0 / 100));
        int g2 = (int) ((1 + 30.0 / 100) * (g - 127.5) + 127.5 * (1 + 0 / 100));
        int b2 = (int) ((1 + 30.0 / 100) * (b - 127.5) + 127.5 * (1 + 0 / 100));

        if (r2 > 255) {
            r2 = 255;
        }
        if (r2 < 0) {
            r2 = 0;
        }
        if (g2 > 255) {
            g2 = 255;
        }
        if (g2 < 0) {
            g2 = 0;
        }
        if (b2 > 255) {
            b2 = 255;
        }
        if (b2 < 0) {
            b2 = 0;
        }
        argb = a | (r2 << 16) | (g2 << 8) | b2;
        image.setRGB(0, 0, argb);

        
        BufferedImage image2 = c.apply(image);

        
        int appliedViaConstructor = image2.getRGB(0,0);
        int appliedViaEquation = image.getRGB(0, 0);
        Assertions.assertEquals(appliedViaEquation, appliedViaConstructor);
    }

    @Test
    void brightnesAdjustTest() {
        ContrastBrightnessAdjust c = new ContrastBrightnessAdjust(0, 30);

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Color testColor = new Color(100, 100, 100);
        image.setRGB(0, 0, testColor.getRGB());
        int argb = image.getRGB(0, 0);

        int a = (argb & 0xFF000000);
        int r = (argb & 0x00FF0000) >> 16;
        int g = (argb & 0x0000FF00) >> 8;
        int b = (argb & 0x000000FF);

        int r2 = (int) ((1 + 0 / 100) * (r - 127.5) + 127.5 * (1 + 30.0 / 100));
        int g2 = (int) ((1 + 0 / 100) * (g - 127.5) + 127.5 * (1 + 30.0 / 100));
        int b2 = (int) ((1 + 0 / 100) * (b - 127.5) + 127.5 * (1 + 30.0 / 100));

        if (r2 > 255) {
            r2 = 255;
        }
        if (r2 < 0) {
            r2 = 0;
        }
        if (g2 > 255) {
            g2 = 255;
        }
        if (g2 < 0) {
            g2 = 0;
        }
        if (b2 > 255) {
            b2 = 255;
        }
        if (b2 < 0) {
            b2 = 0;
        }
        argb = a | (r2 << 16) | (g2 << 8) | b2;
        image.setRGB(0, 0, argb);

       
        BufferedImage image2 = c.apply(image);
        
        int appliedViaConstructor = image2.getRGB(0, 0);
        int appliedViaEquation = image.getRGB(0, 0);
        Assertions.assertEquals(appliedViaEquation, appliedViaConstructor);
    }
}
