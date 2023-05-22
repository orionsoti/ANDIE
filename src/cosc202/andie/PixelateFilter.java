package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * Creates a pixelated version of an image. The image is first reduced to the
 * specified width and height, then scaled back up to the original size.
 * </p>
 * 
 * @see ImageOperation
 * @see java.io.Serializable
 */
public class PixelateFilter implements ImageOperation, java.io.Serializable {

    private int newWidth, newHeight;

    /**
     * <p>
     * Creates a new PixelateFilter with the specified width and height.
     * </p>
     * 
     * @param newWidth The new width of the Image 
     * @param newHeight The new height of the Image
     */
    public PixelateFilter(int newWidth, int newHeight) {
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }
    

    /**
     * Applies the pixelate filter to the input image.
     * 
     * @param input the image to apply the filter to
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        int originalWidth = input.getWidth();
        int originalHeight = input.getHeight();

        // Reduce resolution
        BufferedImage reducedResolutionImage = reduceResolution(input, newWidth, newHeight);

        // Scale it back up to the original size
        BufferedImage pixelArtImage = increaseResolution(reducedResolutionImage, originalWidth, originalHeight);

        return pixelArtImage;
    }

    /**
     * <p>
     * Reduces the resolution of the input image to the specified width and height.
     * </p>
     * 
     * @param originalImage
     * @param width
     * @param height
     * @return
     */
    private BufferedImage reduceResolution(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();

        // Used to avoid blurring when resizing, so that it maintains the pixelated look
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    /**
     * <p>
     * Increases the resolution of the input image to the specified width and
     * height.
     * </p>
     * 
     * @param originalImage the image the filter is being applied to
     * @param width the width of the original image
     * @param height the height of the original image
     */
    private BufferedImage increaseResolution(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        // Used to avoid blurring when resizing, so that it maintains the pixelated look
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
