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
}
