package cosc202.andie.test.cosc202.andie;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.Color;


import cosc202.andie.Flip;

public class FlipTest {

    @Test
    void flipVerticalTest(){
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0,10, 10); 
        g2d.dispose();
        int expected = image.getRGB(0, 0);
        int bottom = image.getRGB(0, 90);
        Flip f = new Flip(1);
        BufferedImage flippedImage = f.apply(image);
        int flipped = flippedImage.getRGB(0,90);
        int top = flippedImage.getRGB(0, 0);
        Assertions.assertEquals(expected, flipped);
        Assertions.assertEquals(top, bottom);



    }
    @Test
    void flipHorizontalTest(){
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
       
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0,10, 10); 
        g2d.dispose();

        int expected = image.getRGB(0, 0);
        int right = image.getRGB(90, 0);
        
        Flip f = new Flip(-1);
        BufferedImage flippedImage = f.apply(image);
        
        int flipped = flippedImage.getRGB(90,0);
        int left = flippedImage.getRGB(0, 0);
       
        Assertions.assertEquals(expected, flipped);
        Assertions.assertEquals(left, right);
    }
    @Test
    void doubleFlipTest(){
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
       
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0,10, 10); 
        g2d.dispose();

        int expected = image.getRGB(0, 0);
        int expectedRight = image.getRGB(90, 0);
        
        Flip f = new Flip(-1);

        BufferedImage flippedImage = f.apply(image);
        flippedImage = f.apply(flippedImage);
        
        int doubleFlipped = flippedImage.getRGB(0,0);
        int currentRight = flippedImage.getRGB(90, 0);
       
        Assertions.assertEquals(expected, doubleFlipped);
        Assertions.assertEquals(expectedRight, currentRight);
    }
    
    
}
