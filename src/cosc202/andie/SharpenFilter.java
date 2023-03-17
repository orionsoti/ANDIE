package cosc202.andie;

import java.awt.image.*;

public class SharpenFilter implements ImageOperation, java.io.Serializable{
    /**
     * <p>
     * Constructor for sharpen filter
     * <p>
     */
    SharpenFilter(){}

    /**
     * <p>
     * Apply a sharpen filter to an image.
     * </p>
     * 
     * @param input The image to be sharpened.
     * @return The sharpened image as output.
     */
    public BufferedImage apply(BufferedImage input) {
        float [] array = new float[]{
            0.0f, -0.5f, 0.0f,
            -0.5f, 3.0f, -0.5f,
            0.0f, -0.5f, 0.0f
        };
        //Create the ConvolveOp object with the kernel
        Kernel kernel = new Kernel(3, 3, array);
        ConvolveOp convOp = new ConvolveOp(kernel);

        // Creates a copy of the buffered image
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
                
        // Applies the convolution to the input, outputting the result to the output image.
        convOp.filter(input, output);

        return output;
    }
    
}
