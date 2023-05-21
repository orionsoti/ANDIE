package cosc202.andie.test.cosc202.andie;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.Crop;
import java.awt.Rectangle;
    

public class CropTest {

    @Test
    void noCropTest(){
        
        BufferedImage b = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        
        int initialW = b.getWidth();
        int initialH = b.getHeight();
        
        Rectangle r = new Rectangle(0, 0, 100, 100);

        Crop c = new Crop(r,1,0,0);
        c.apply(b);
        int postCropWidth = b.getWidth();
        int postCropHeight = b.getHeight();
      
        Assertions.assertEquals(initialW, postCropWidth);
        Assertions.assertEquals(initialH,postCropHeight);



    }
    @Test
    void CropTestWidth(){
        
        BufferedImage b = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        
        int initialH = b.getHeight();
        
        Rectangle r = new Rectangle(0, 0, 50, 100); // for bounds

        Crop c = new Crop(r,1,0,0);
        BufferedImage cropped = c.apply(b);
        
        int expectedW = (int)r.getWidth();
        int postCropWidth = cropped.getWidth();
        int postCropHeight = cropped.getHeight();
      
        Assertions.assertEquals(expectedW, postCropWidth);
        Assertions.assertEquals(initialH,postCropHeight);



    }
    @Test
    void CropTestHeight(){
        
        BufferedImage b = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        
        int initialW = b.getWidth();
        
        Rectangle r = new Rectangle(0, 0, 100, 50); 

        Crop c = new Crop(r,1,0,0);
        BufferedImage cropped = c.apply(b);
        
        int expectedH = (int)r.getHeight();
        int postCropWidth = cropped.getWidth();
        int postCropHeight = cropped.getHeight();
      
        Assertions.assertEquals(expectedH, postCropHeight);
        Assertions.assertEquals(initialW, postCropWidth);



    }
    @Test
    void cropTestWidthAndHeight(){
        
        BufferedImage b = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        
        
        Rectangle r = new Rectangle(0, 0, 50, 50); 

        Crop c = new Crop(r,1,0,0);
        BufferedImage cropped = c.apply(b);
        
        int expectedH = (int)r.getHeight();
        int expectedW = (int)r.getWidth();
        int postCropWidth = cropped.getWidth();
        int postCropHeight = cropped.getHeight();
      
        Assertions.assertEquals(expectedH, postCropHeight);
        Assertions.assertEquals(expectedW, postCropWidth);



    }
    @Test
    void outOfBoundsTest(){
        
        BufferedImage b = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        
        
        Rectangle r = new Rectangle(0, 0, 250, 250); 

        Crop c = new Crop(r,1,0,0);
        BufferedImage cropped = c.apply(b);
        
        int expectedH = b.getHeight();
        int expectedW = b.getWidth();
        int postCropWidth = cropped.getWidth();
        int postCropHeight = cropped.getHeight();
      
        Assertions.assertEquals(expectedH, postCropHeight);
        Assertions.assertEquals(expectedW, postCropWidth);



    }
}
