package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * <p>
 * A custom implementation of image convolution operator.
 * </p>
 * 
 * @author Orion Soti
 */
public class ConvolutionOperation {

    private Kernel kernel;
    private float[] kernelData;

    /**
     * Constructor that initialises the kernel and kernel data.
     * 
     * @param kernel the kernel to be used for convolution
     */
    public ConvolutionOperation(Kernel kernel) {
        this.kernel = kernel;
        kernelData = new float[kernel.getHeight() * kernel.getWidth()];
        kernel.getKernelData(kernelData);
    }

    /**
     * <p>
     * Applies the convolution operation to the input image and returns the output
     * image.
     * </p>
     * 
     * @param inputImage  the image to be convolved
     * @param outputImage the image after applying the convolution
     * @return the filtered output image
     */
    public BufferedImage filter(BufferedImage inputImage, BufferedImage outputImage) {
        float sum = 0;
        for (float value : kernelData) {
            sum += value;
        }
        switch (Math.round(sum)) {
            // if the sum is 0, then image is convolved with the values offset by 128
            case 0:
                outputImage = filterOffset(inputImage, outputImage);
                break;
            // if the sum is 1, then image is convolved using java's inbuilt convolution
            // operation but with the edge case accounted for
            case 1:
                outputImage = filterEdgeCase(inputImage, outputImage);
                break;
            // default to case 1
            default:
                outputImage = filterEdgeCase(inputImage, outputImage);
                break;
        }
        return outputImage;
    }

    /**
     * Filters the image using Java's inbuilt convolution operation and the edge
     * case accounted for.
     * 
     * @param inputImage  the image to be convolved
     * @param outputImage the image after applying the convolution
     * @return the filtered output image
     */
    private BufferedImage filterEdgeCase(BufferedImage inputImage, BufferedImage outputImage) {
        int width = inputImage.getWidth() + (kernel.getWidth() - 1);
        int height = inputImage.getHeight() + (kernel.getHeight() - 1);
        BufferedImage enlargedImage = new BufferedImage(width, height, inputImage.getType());
        Image scaledImage = inputImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        Graphics2D g = enlargedImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.drawImage(inputImage, (kernel.getWidth() - 1) / 2, (kernel.getHeight() - 1) / 2, null);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        op.filter(enlargedImage, outputImage);
        return outputImage;
    }

    /**
     * Filters the image by convolving it using the kernel values offset by 128.
     * 
     * @param inputImage  the image to be convolved
     * @param outputImage the image after applying the convolution
     * @return the filtered output image
     */
    private BufferedImage filterOffset(BufferedImage inputImage, BufferedImage outputImage) {
        if (inputImage == null) {
            throw new NullPointerException("no input image");
        }
        if (inputImage == outputImage) {
            throw new IllegalArgumentException("input and output images must be different");
        }
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int radius = (kernel.getWidth() - 1) / 2;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                float alpha = 128;
                float red = 128;
                float green = 128;
                float blue = 128;
                int kernelIndex = 0;
                for (int dy = -radius; dy <= radius; ++dy) {
                    for (int dx = -radius; dx <= radius; ++dx) {
                        int ydy = Math.min(Math.max(0, y + dy), height - 1);
                        int xdx = Math.min(Math.max(0, x + dx), width - 1);
                        int pixel = inputImage.getRGB(xdx, ydy);
                        float kernelValue = kernelData[kernelIndex];
                        kernelIndex++;
                        alpha += ((pixel & 0xFF000000) >> 24) * kernelValue;
                        red += ((pixel & 0x00FF0000) >> 16) * kernelValue;
                        green += ((pixel & 0x0000FF00) >> 8) * kernelValue;
                        blue += ((pixel & 0x000000FF)) * kernelValue;
                    }
                }
                int alphaInt = truncate((int) alpha);
                int redInt = truncate((int) red);
                int greenInt = truncate((int) green);
                int blueInt = truncate((int) blue);
                int outputPixel = (alphaInt << 24) | (redInt << 16) | (greenInt << 8) | blueInt;
                outputImage.setRGB(x, y, outputPixel);
            }
        }
        return outputImage;
    }

    /**
     * Truncates the input value to be between 0 and 255.
     * 
     * @param input the input value to be truncated
     * @return the truncated value between 0 and 255
     */
    private int truncate(int input) {
        if (input < 0) {
            return 0;
        } else if (input > 255) {
            return 255;
        } else {
            return input;
        }
    }
}
