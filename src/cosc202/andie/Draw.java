package cosc202.andie;


import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.color.*;

/**<p>
 * Fuction to allow Draw capibilities
 * </p>
 * <p>The Draw class implements an image drawing operation that allows drawing a
 * specific Shape from a given input image and returns the image.
 * </p>

 */
public class Draw implements ImageOperation, java.io.Serializable {

    private Shape selection;
    private double scale;
    private int x;
    private int y;
    
    private boolean line;
    private boolean rectangle;
    private boolean oval;


    /**
     * Constructs a Draw object with the specified Shape selection, scale,
     * and offsets.
     *
     * @param selection the Shape to Draw from the input image
     * @param scale     the scale factor to apply to the input image
     * @param x   the horizontal offset to apply to the input image
     * @param y   the vertical offset to apply to the input image
     */
    public Draw(Rectangle selection, double scale, int x, int y, boolean rectangle, boolean line, boolean oval) {
        
        this.selection = selection;
        this.scale = scale;
        this.x = x;
        this.y = y;
        
        this.rectangle = rectangle;
        this.line = line;
        this.oval = oval;
    }

    /**
     * Applies the Drawing operation on the input image and returns the image.
     *
     * @param input the input BufferedImage to Draw
     * @return the new BufferedImage
     */
    public BufferedImage apply(BufferedImage input) {
        
      Graphics2D inputGraphics = getScaledGraphics(input);
        //Graphics2D inputGraphics = input.createGraphics();

        Shape transformedSelection = inputGraphics.getTransform().createTransformedShape(selection);
        Rectangle bounds = transformedSelection.getBounds();
        
        int nx = bounds.x; // otherwise when undo is called the shape will be moved**
        int ny = bounds.y;
        int widthR = bounds.width;
        int heightR = bounds.height;
        int maxX = (int)bounds.getMaxX();
        int maxY = (int)bounds.getMaxY();

     
        Graphics2D outputGraphics = input.createGraphics();
       
        if(rectangle) outputGraphics.drawRect(nx, ny, widthR, heightR);
        if(oval) outputGraphics.drawOval(nx, ny, widthR, heightR);
        if(line) outputGraphics.drawLine(nx, ny, widthR, heightR);

        outputGraphics.setColor(new Color(252, 138, 22, 1));
        inputGraphics.dispose();
        outputGraphics.dispose();
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
