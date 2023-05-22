
package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * Gaussian Blur filter for images.
 * </p>
 * 
 * @author Orion Soti
 * @version 1.0
 *          2 April 2023
 * 
 */
public class GaussianBlurFilter implements ImageOperation, java.io.Serializable {
    private int radius;

    /**
     * <p>
     * Constructor for Gaussian Blur filter
     * <p>
     */
    public GaussianBlurFilter() {
        this(1);
    }

    /**
     * <p>
     * Constructor for Gaussian Blur filter
     * <p>
     * 
     * 
     * @param radius The radius of the Gaussian Blur filter
     */
    public GaussianBlurFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Apply a Gaussian Blur filter to an image.
     * </p>
     * 
     * @param input The image to be blurred.
     * @return The blurred image as output.
     */

    public BufferedImage apply(BufferedImage input) {
        // Create a kernel using the Gaussian function
        int size = (2 * radius + 1);
        float[] array = new float[size * size];
        float sum = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                // Calculates the Gaussian function for the current x and y values
                array[(x + radius) * size + (y + radius)] = gaussianFunction(x, y);
                // Adds the value to the sum
                sum += array[(x + radius) * size + (y + radius)];
            }
        }
        // Normalise the array so that the sum of the values is 1
        for (int i = 0; i < array.length; i++) {
            array[i] /= sum;
        }
        // Creates a convolution kernel with the calculated values
        Kernel kernel = new Kernel(size, size, array);
        // Creates a new convolution operation with the kernel
        ConvolutionOperation convOp = new ConvolutionOperation(kernel);
        // Creates a copy of the input image to apply the filter to
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        // Applies the filter
        convOp.filter(input, output);
        return output;

    }

    /**
     * <p>
     * Calculates the Gaussian function for the given x and y values
     * </p>
     * 
     * @param x The x value
     * @param y The y value
     * @return The Gaussian function value
     * 
     */
    protected float gaussianFunction(int x, int y) {
        // The variance is set to 1/3 of the radius
        double variance = (radius / 3.0);
        // Calculates the Gaussian function
        double output = (1 / (2 * Math.PI * Math.pow(variance, 2))
                * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(variance, 2))));
        // Returns the result as a float
        return (float) (output);
    }
}
