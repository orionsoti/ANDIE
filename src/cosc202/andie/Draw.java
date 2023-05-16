package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

/**
 * Function to allow Draw capabilities
 * The Draw class implements an image drawing operation that allows drawing a specific Shape or lines
 * from a given input image and returns the modified image.
 */
public class Draw implements ImageOperation, java.io.Serializable {

    private Shape selection;
    private double scale;
    private int x;
    private int y;
    private boolean rectangle;
    private boolean oval;
    private boolean line;
    private int lineX1;
    private int lineY1;
    private int lineX2;
    private int lineY2;
    boolean fill;

    /**
     * Constructs a Draw object with the specified Shape selection, scale, offsets, and line coordinates.
     *
     * @param selection the Shape to draw from the input image
     * @param scale     the scale factor to apply to the input image
     * @param x         the horizontal offset to apply to the input image
     * @param y         the vertical offset to apply to the input image
     * @param rectangle a flag indicating whether to draw rectangles
     * @param oval      a flag indicating whether to draw ovals
     * @param line      a flag indicating whether to draw lines
     * @param lineX1    the x-coordinate of the starting point of the line
     * @param lineY1    the y-coordinate of the starting point of the line
     * @param lineX2    the x-coordinate of the ending point of the line
     * @param lineY2    the y-coordinate of the ending point of the line
     */
    public Draw(Shape selection, double scale, int x, int y, boolean rectangle, boolean oval,
                boolean line, int lineX1, int lineY1, int lineX2, int lineY2, boolean fill) {
        this.selection = selection;
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.rectangle = rectangle;
        this.oval = oval;
        this.line = line;
        this.lineX1 = lineX1;
        this.lineY1 = lineY1;
        this.lineX2 = lineX2;
        this.lineY2 = lineY2;
        this.fill = fill;
    }

    /**
     * Applies the drawing operation on the input image and returns the modified image.
     *
     * @param input the input BufferedImage to draw on
     * @return the modified BufferedImage
     */
    public BufferedImage apply(BufferedImage input) {
        Graphics2D inputGraphics = getScaledGraphics(input);

        if (rectangle) {
            if (fill) {
                inputGraphics.fill(selection);
            }
            else {
                inputGraphics.draw(selection);
            }
        }
        if (oval) {
            if (fill) {
                inputGraphics.fill(selection);
            }
            else {
                inputGraphics.draw(selection);
            }
        }
        if (line) {
            Stroke stroke = new BasicStroke(2); // Set the line thickness
            inputGraphics.setStroke(stroke);
            inputGraphics.drawLine(lineX1, lineY1, lineX2, lineY2);
        }

        inputGraphics.dispose();
        return input;
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
