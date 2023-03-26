package cosc202.andie;

import java.awt.*;
import java.awt.image.*;

/* 
 * <p>
 * Resize the image to a new width and height.
 * </p>
 * 
 */
public class Resize implements ImageOperation, java.io.Serializable{
    public int height;
    public int width;
    public double scale;

    /**
     * <p>
     * Create a new Resize operation.
     * </p>
     */
    public Resize(int height, int width, double scale) {
        this.height = height;
        this.width = width;
        this.scale = scale;
    }

    /**
     * <p>
     * Resize the image to a new width and height.
     * </p>
     * @param input The image to resize
     */
    public BufferedImage apply(BufferedImage input) {
        Image resized = input.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage output = new BufferedImage(width, height, input.getType());
        output.getGraphics().drawImage(resized, 0, 0, null);
        return output;
    }
    
}
