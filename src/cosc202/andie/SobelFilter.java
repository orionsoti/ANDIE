package cosc202.andie;

import java.awt.image.*;

/**
 * This class applies Sobel filter on a given image in either horizontal or vertical direction.
 * The Sobel filter is used to detect edges in an image.
 * 
 * @author Orion Soti
 */
public class SobelFilter implements ImageOperation, java.io.Serializable {

    private boolean direction;
    public static final boolean VERTICAL = true;
    public static final boolean HORIZONTAL = false;

    // Sobel filter kernels for vertical and horizontal directions
    private static final float[][] KERNELARRAYS = {
            { -1 / 2f, 0, 1 / 2f, -1, 0, 1, -1 / 2f, 0, 1 / 2f },   
            { -1 / 2f, -1, -1 / 2f, 0, 0, 0, 1 / 2f, 1, 1 / 2f }
    };

    /**
     * Constructor to create a SobelFilter object with a specified direction.
     * @param direction The direction of the filter, either VERTICAL or HORIZONTAL.
     */
    public SobelFilter(boolean direction) {
        this.direction = direction;
    }

    /**
     * Default constructor creates a SobelFilter object with vertical direction.
     */
    public SobelFilter() {
        this.direction = HORIZONTAL;
    }

    /**
     * Applies the Sobel filter to the input image in either vertical or horizontal direction.
     * @param input The input BufferedImage to apply the filter to.
     * @return The output BufferedImage with the Sobel filter applied.
     */
    public BufferedImage apply(BufferedImage input) {
        float[] array = direction == VERTICAL ? KERNELARRAYS[1] : KERNELARRAYS[0];
        Kernel kernel = new Kernel(3, 3, array);
        ConvolutionOperation convOp = new ConvolutionOperation(kernel);
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        convOp.filter(input, output);
        return output;
    }
}

