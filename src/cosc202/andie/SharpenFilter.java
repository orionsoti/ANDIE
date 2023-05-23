package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * Sharpen filter for images.
 * </p>
 * 
 * @author Orion Soti
 * @version 1.0
 *          2 April 2023
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    /**
     * <p>
     * Constructor for sharpen filter
     * <p>
     */
    SharpenFilter() {
    }

    /**
     * <p>
     * Apply a sharpen filter to an image.
     * </p>
     * 
     * @param input The image to be sharpened.
     * @return The sharpened image as output.
     */
    public BufferedImage apply(BufferedImage input) {
        // Create a sharpening kernel
        float[] array = new float[] {
                0.0f, -0.5f, 0.0f,
                -0.5f, 3.0f, -0.5f,
                0.0f, -0.5f, 0.0f
        };

        // Apply the kernel to the image
        Kernel kernel = new Kernel(3, 3, array);
        ConvolutionOperation convOp = new ConvolutionOperation(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);
        return output;
    }

}
