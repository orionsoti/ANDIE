package cosc202.andie;

import java.awt.image.*;
import java.awt.*;

public class GaussianBlurFilter implements ImageOperation, java.io.Serializable{
    private int radius;
    //Default constructor initialises radius to 1
    GaussianBlurFilter(){
        this(1);
    }

    //Constructor that takes in a radius
    GaussianBlurFilter(int radius){
        this.radius = radius;
    }

    //Applies the Gaussian Blur filter to the input image
    public BufferedImage apply(BufferedImage input){
         // Create a kernel using the Gaussian function
        int size = (2 * radius + 1);
        float[] array = new float[size * size];
        float sum = 0;
        for (int x = -radius; x <=radius; x++) {
            for (int y = -radius; y <=radius; y++) {
                //Calculates the Gaussian function for the current x and y values
                array[(x+radius)*size+(y+radius)] = gaussianFunction(x,y);
                //Adds the value to the sum
                sum += array[(x+radius)*size+(y+radius)];
            }
        }
        //Normalise the array so that the sum of the values is 1
        for (int i = 0; i < array.length; i++) {
            array[i] /= sum;
        }
        
        //Creates a convolution kernel with the calculated values
        Kernel kernel = new Kernel(size, size, array);
        //Creates a new convolution operation with the kernel
        ConvolveOp convOp = new ConvolveOp(kernel);
        //Calculates size of borders and new image dimensions
        int border = radius * 2;
        int newWidth = input.getWidth() + border;
        int newHeight = input.getHeight() + border;
        //Creates a new BufferedImage with padded dimensions
        BufferedImage paddedInput = new BufferedImage(newWidth, newHeight, input.getType());
        Graphics2D g = paddedInput.createGraphics();
        g.drawImage(input, radius, radius, null);
        g.dispose();
        //Apply the convolution operation to the input image
        BufferedImage output = convOp.filter(paddedInput, null);
        //Crops the output image to the original size
        output = output.getSubimage(radius, radius, input.getWidth(), input.getHeight());
        //Returns the output image
        return output;
        
        /* UNUSED CODE - This code outputs the same image above but with black borders
        //Creates a new BufferedImage to store the output
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);*/
    }
    
    //Calculates the Gaussian function for a given x and y value
    protected float gaussianFunction(int x, int y) {
        //The variance is set to 1/3 of the radius
        double variance = (radius / 3.0);
        //Calculates the Gaussian function
        double output = (1 / (2 * Math.PI * Math.pow(variance, 2))
                * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(variance, 2))));
        //Returns the result as a float
        return (float) (output);
    }
}


    

