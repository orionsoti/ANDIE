package cosc202.andie.test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ImagePanel;

public class imagePanelTest {
    @Test
    void initialDummyTest(){

    }

    @Test
    void getZoomInitialValue(){
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100, testPanel.getZoom());
        
    }
}
