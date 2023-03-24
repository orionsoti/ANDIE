package cosc202.andie;

import java.awt.image.*;

/**
 * Flip image horizontally or vertically
 */
public class Flip implements ImageOperation, java.io.Serializable{
    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = -1;
    private int direction;

    /**
     * Default constructor flips image vertically
     */
    Flip(){
        this.direction = FLIP_VERTICAL;
    }

    /**
     * Constructor that takes an int argument that determines the direction of the flip
     * @param direction
     */
    Flip(int direction){
        this.direction = direction;
    }

    public BufferedImage apply(BufferedImage input){
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                switch (direction){
                    case FLIP_VERTICAL:
                        flipped.setRGB(x, y, input.getRGB(x, height-y-1));
                        break;
                    case FLIP_HORIZONTAL:
                        flipped.setRGB(x, y, input.getRGB(width-x-1, y));
                        break;
                }
            }
        }
        return flipped;
    }

}


    

