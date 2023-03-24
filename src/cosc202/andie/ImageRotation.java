package cosc202.andie;

import java.awt.image.*;
// import java.util.*;

public class ImageRotation implements ImageOperation, java.io.Serializable{
    int rotation;

    ImageRotation(int rotation){
        this.rotation = rotation;
    }

    public BufferedImage apply(BufferedImage input){
        BufferedImage output;

        switch(rotation){
            case 1: // rotate 90 dergees to the right
                output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
                for(int x = 0; x < input.getWidth(); x++){
                    for(int y = 0; y < input.getHeight(); y++){
                        output.setRGB(input.getHeight() -1 - y, x, input.getRGB(x, y));
                    }
                }
                break;
            case 2: // rotate 90 degrees left (or 270 degrees right cause im never wrong)
                output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
                for(int x = 0; x < input.getWidth(); x++){
                    for(int y = 0; y < input.getHeight(); y++){
                        output.setRGB(y, input.getWidth() - 1 - x, input.getRGB(x, y));
                    }
                }
                break;
            default: //Rotates image 90 degrees to the right on default
                output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
                for(int x = 0; x < input.getWidth(); x++){
                    for(int y = 0; y < input.getHeight(); y++){
                        output.setRGB(input.getHeight() -1 - y, x, input.getRGB(x, y));
                    }
                }
                break;
        }
        return output;
    }
}