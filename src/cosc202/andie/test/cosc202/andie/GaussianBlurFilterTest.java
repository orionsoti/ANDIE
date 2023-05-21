package cosc202.andie.test.cosc202.andie;


    import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.Color;

import cosc202.andie.GaussianBlurFilter;


public class GaussianBlurFilterTest {
    @Test
    void radiusAtZeroTest(){
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0,10, 10); 
        g2d.dispose();
        int expected = image.getRGB(10, 5);
        GaussianBlurFilter g = new GaussianBlurFilter(0);
        BufferedImage blurredImage = g.apply(image);
        int defaultBlurApplied = blurredImage.getRGB(10,5);
        Assertions.assertEquals(expected, defaultBlurApplied);
        
    }

    @Test
    void filterAppliedTest(){
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0,20, 20);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(20,0,20, 20); 
        g2d.fillRect(0,20,20, 20);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(20,20,20, 20);
        g2d.dispose();
        
        int expected = image.getRGB(10, 5);
        int blurLine1 = image.getRGB(20, 0);
        int blurLine2 = image.getRGB(20, 20);
        
        GaussianBlurFilter g = new GaussianBlurFilter(5);
        BufferedImage blurredImage = g.apply(image);
        
        int blurApplied = blurredImage.getRGB(10,5);
        Assertions.assertNotEquals(expected, blurApplied);
        int colorBlur1 = blurredImage.getRGB(20, 0);
        int colorBlur2 = blurredImage.getRGB(20, 20);
        Assertions.assertTrue(blurLine1 != colorBlur1);
        Assertions.assertTrue(blurLine2 != colorBlur2);
    }
    // @Test
    // void radiusTooLarge(){
    //     BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    //     Graphics2D g2d = image.createGraphics();
    //     g2d.setColor(Color.GREEN);
    //     g2d.fillRect(0,0,20, 20);
    //     g2d.setColor(Color.WHITE);
    //     g2d.fillRect(20,0,20, 20); 
    //     g2d.fillRect(0,20,20, 20);
    //     g2d.setColor(Color.GREEN);
    //     g2d.fillRect(20,20,20, 20);
    //     g2d.dispose();
    //     int expected = image.getRGB(20, 0);
    //     GaussianBlurFilter g = new GaussianBlurFilter(200);
    //     BufferedImage blurredImage = g.apply(image);
    //     int blurApplied = blurredImage.getRGB(20,0);
    //     Assertions.assertEquals(expected, blurApplied);
        
    // }

}
