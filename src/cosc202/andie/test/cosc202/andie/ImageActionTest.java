package cosc202.andie.test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ImageAction;
import cosc202.andie.ImagePanel;

public class ImageActionTest {
    
    ImagePanel testPanel = new ImagePanel();
    
    @Test
    void setAndGetPanelTest(){
        ImageAction.setTarget(testPanel);
        ImagePanel comparedPanel = ImageAction.getTarget();
        Assertions.assertEquals(testPanel, comparedPanel);

    }    
}
