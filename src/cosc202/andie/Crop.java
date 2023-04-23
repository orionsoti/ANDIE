package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * The Crop class implements an image cropping operation that allows cropping a
 * specific Shape from a given input image and returns the cropped image.
 * 
 * @Author Orion Soti
 */
public class Crop implements ImageOperation, java.io.Serializable {

    private Shape selection;
    private double scale;
    private int x;
    private int y;

    /**
     * Constructs a Crop object with the specified Shape selection, scale,
     * and offsets.
     *
     * @param selection the Shape to crop from the input image
     * @param scale     the scale factor to apply to the input image
     * @param x   the horizontal offset to apply to the input image
     * @param y   the vertical offset to apply to the input image
     */
    public Crop(Rectangle selection, double scale, int x, int y) {
        this.selection = selection;
        this.scale = scale;
        this.x = x;
        this.y = y;
    }

    /**
     * Applies the cropping operation on the input image and returns the cropped image.
     *
     * @param input the input BufferedImage to crop
     * @return the cropped BufferedImage
     */
    public BufferedImage apply(BufferedImage input) {
        Graphics2D inputGraphics = getScaledGraphics(input);
        Shape transformedSelection = inputGraphics.getTransform().createTransformedShape(selection);
        Rectangle bounds = transformedSelection.getBounds();

        if (!input.getData().getBounds().contains(bounds)) {
            inputGraphics.dispose();
            return input;
        }

        BufferedImage sub = input.getSubimage(bounds.x, bounds.y, bounds.width, bounds.height);
        BufferedImage output = new BufferedImage(sub.getWidth(), sub.getHeight(), sub.getType());
        Graphics2D outputGraphics = output.createGraphics();
        outputGraphics.drawImage(sub, 0, 0, null);
        inputGraphics.dispose();
        outputGraphics.dispose();
        return output;
    }

    /**
     * Returns a Graphics2D object with the appropriate scaling and translation
     * applied based on the scale and offsets specified in the constructor.
     *
     * @param input the input BufferedImage to create a Graphics2D object from
     * @return the Graphics2D object with scaling and translation applied
     */
    private Graphics2D getScaledGraphics(BufferedImage input) {
        Graphics2D g2d = input.createGraphics();
        int halfWidth = input.getWidth() / 2;
        int halfHeight = input.getHeight() / 2;
        g2d.translate(halfWidth, halfHeight);
        g2d.scale(1 / scale, 1 / scale);
        g2d.translate(-halfWidth, -halfHeight);
        g2d.translate(-x, -y);
        return g2d;
    }
}
