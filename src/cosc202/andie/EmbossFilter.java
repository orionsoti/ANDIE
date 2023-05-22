package cosc202.andie;

import java.awt.image.*;

/**
 * This class applies an emboss effect to a given BufferedImage.
 * The emboss effect creates a 3D-like appearance by highlighting the edges in
 * the image
 * based on the specified direction.
 * 
 * @author Orion Soti
 */
public class EmbossFilter implements ImageOperation, java.io.Serializable {

    private int direction;

    public static final int WEST = 0;
    public static final int NORTHWEST = 1;
    public static final int NORTH = 2;
    public static final int NORTHEAST = 3;
    public static final int EAST = 4;
    public static final int SOUTHEAST = 5;
    public static final int SOUTH = 6;
    public static final int SOUTHWEST = 7;

    // A 2D array of floats representing the kernel matrix for the emboss effect
    // based on the specified direction
    private static final float[][] KERNELS = {
            { 0, 0, 0, 1, 0, -1, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0, -1 },
            { 0, 1, 0, 0, 0, 0, 0, -1, 0 },
            { 0, 0, 1, 0, 0, 0, -1, 0, 0 },
            { 0, 0, 0, -1, 0, 1, 0, 0, 0 },
            { -1, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 0, -1, 0, 0, 0, 0, 0, 1, 0 },
            { 0, 0, -1, 0, 0, 0, 1, 0, 0 },

    };

    /**
     * Constructor that sets the direction of the emboss effect.
     *
     * @param direction The direction of the emboss effect.
     */
    public EmbossFilter(int direction) {
        this.direction = direction;
    }

    /**
     * Default constructor that sets the emboss effect direction to NORTH.
     */
    public EmbossFilter() {
        this(NORTH);
    }

    /**
     * Applies the emboss effect to a given BufferedImage.
     *
     * @param input The input BufferedImage to apply the emboss effect to.
     * @return A BufferedImage with the emboss effect applied.
     */
    public BufferedImage apply(BufferedImage input) {
        float[] kernelArray = KERNELS[direction];
        Kernel kernel = new Kernel(3, 3, kernelArray);
        ConvolutionOperation convOp = new ConvolutionOperation(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);
        return output;
    }

}
