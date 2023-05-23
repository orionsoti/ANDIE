package cosc202.andie.test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ImagePanel;

public class imagePanelTest {

    @Test
    void getZoomInitialValue(){
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100, testPanel.getZoom());    
    }

    @Test
    void getUpperLimitZoomTest(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(400);
        Assertions.assertEquals(200, testPanel.getZoom());    
    }

    @Test
    void getLowerLimitZoomTest(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(20);
        Assertions.assertEquals(50, testPanel.getZoom());   
    }
    @Test 
    void cropModeTest(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setCropMode(true);
        Assertions.assertTrue(testPanel.getCropMode());
    }
    @Test 
    void drawModeTest(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setDrawMode(true,false,false,false);
        Assertions.assertTrue(testPanel.getDrawMode());
    }
    @Test 
    void lineThicknessTest(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setLineThickness(20);
        float f = 20F;
        Assertions.assertEquals(f, testPanel.getLineThickness());
    }

    }

