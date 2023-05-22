package cosc202.andie.test.cosc202.andie;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.Color;


import cosc202.andie.ImageRotation;

public class ImageRotationTest {
   
   
    @Test
    void rotateRightTest(){
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
       
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0,10, 10); 
        g2d.dispose();
       
        int expected = image.getRGB(0, 0);
        int bottomLeft = image.getRGB(0,90);
       
        ImageRotation r = new ImageRotation(1);
        BufferedImage rotatedImage = r.apply(image);
        int rotated = rotatedImage.getRGB(90,0);
        int topLeft = rotatedImage.getRGB(0, 0);
       
        Assertions.assertEquals(expected, rotated);
        Assertions.assertEquals(bottomLeft, topLeft);

    }

    @Test
    void rotateLeftTest(){
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
       
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0,10, 10); 
        g2d.dispose();
       
        int expected = image.getRGB(0, 0);
        int topRight = image.getRGB(90,0);
       
        ImageRotation r = new ImageRotation(2);
        BufferedImage rotatedImage = r.apply(image);
        int rotated = rotatedImage.getRGB(0,90);
        int topLeft = rotatedImage.getRGB(0, 0);
       
        Assertions.assertEquals(expected, rotated);
        Assertions.assertEquals(topRight, topLeft);

    }

    @Test
    void rotate360Test(){
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
       
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0,10, 10); 
        g2d.dispose();
       
        int expected = image.getRGB(0, 0);
        int topRight = image.getRGB(90,0);
       
        ImageRotation r = new ImageRotation(2);
        BufferedImage rotatedImage = r.apply(image);
        for(int i = 0; i < 3; i++){
            rotatedImage = r.apply(rotatedImage);
        }
        int fullRotation = rotatedImage.getRGB(0,0);
        int topRight2 = rotatedImage.getRGB(90, 0);
       
        Assertions.assertEquals(expected, fullRotation);
        Assertions.assertEquals(topRight, topRight2);

    }
    
}
