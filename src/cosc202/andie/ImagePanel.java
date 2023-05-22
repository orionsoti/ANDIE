package cosc202.andie;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

/**
 * <p>
 * UI display element for EditableImages.
 * This class extends JPanel to allow for rendering of an image, as well as
 * zooming in and out.
 * </p>
 */
public class ImagePanel extends JPanel {

    private EditableImage image;
    private double scale;
    private Point selectionStart;
    private Point selectionEnd;
    private boolean isSelecting;
    private boolean cropMode;
    private boolean drawMode;
    private boolean lineMode;
    private boolean rectangleMode;
    private boolean ovalMode;
    private Color color;
    private boolean fillShapes;
    private float lineThickness;

    /**
     * <p>
     * Constructs an ImagePanel with a default EditableImage and scale of 1.0.
     * Also sets up mouse listeners for selection and drawing.
     * </p>
     * 
     * @see EditableImage
     * @see MouseAdapter
     * @see Crop
     * @see Draw
     *
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
        isSelecting = false;
        cropMode = false;
        drawMode = false;
        lineMode = false;
        rectangleMode = false;
        ovalMode = false;
        color = Color.WHITE;
        fillShapes = false;
        lineThickness = 1.0f;

        // Mouse listeners for selection and drawing
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (cropMode || drawMode || lineMode) {
                        if (isPointInImageBounds(e.getPoint())) {
                            isSelecting = true;
                            selectionStart = e.getPoint();
                            selectionEnd = e.getPoint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select a region within the image bounds");
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (cropMode || drawMode || lineMode) {
                        isSelecting = false;
                        if (selectionStart.equals(selectionEnd)) {
                            selectionStart = null;
                            selectionEnd = null;
                        }
                        if (cropMode && getSelectionRectangle() != null) {
                            Rectangle selectionRectangle = getSelectionRectangle();
                            Rectangle cropRectangle = new Rectangle(
                                    (int) (selectionRectangle.x / scale),
                                    (int) (selectionRectangle.y / scale),
                                    (int) (selectionRectangle.width / scale),
                                    (int) (selectionRectangle.height / scale));
                            image.apply(new Crop(cropRectangle, 1.0, 0, 0));
                            resetSelection();
                            setCropMode(false);
                        }
                        if (drawMode && (rectangleMode || ovalMode) && getSelectionRectangle() != null) {
                            Shape selectionShape;
                            if (rectangleMode) {
                                int x = (int) (Math.min(selectionStart.x, selectionEnd.x) / scale);
                                int y = (int) (Math.min(selectionStart.y, selectionEnd.y) / scale);
                                int width = (int) (Math.abs(selectionStart.x - selectionEnd.x) / scale);
                                int height = (int) (Math.abs(selectionStart.y - selectionEnd.y) / scale);
                                selectionShape = new Rectangle(x, y, width, height);
                            } else {
                                int x = (int) (Math.min(selectionStart.x, selectionEnd.x) / scale);
                                int y = (int) (Math.min(selectionStart.y, selectionEnd.y) / scale);
                                int width = (int) (Math.abs(selectionStart.x - selectionEnd.x) / scale);
                                int height = (int) (Math.abs(selectionStart.y - selectionEnd.y) / scale);
                                selectionShape = new Ellipse2D.Double(x, y, width, height);
                            }
                            Color chosenColor = color;
                            float chosenLineThickness = lineThickness;
                            image.apply(new Draw(selectionShape, getZoom() / 100, 0, 0, rectangleMode, ovalMode,
                                    false, 0, 0, 0, 0, fillShapes, chosenColor, chosenLineThickness));
                            setDrawMode(false, false, false, false);
                        }
                        if (lineMode && getSelectionStartPoint() != null && getSelectionEndPoint() != null) {
                            Point startPoint = getSelectionStartPoint();
                            Point endPoint = getSelectionEndPoint();
                            Color chosenColor = color;
                            float chosenLineThickness = lineThickness;
                            image.apply(new Draw(null, getZoom() / 100, 0, 0, false, false,
                                    true, startPoint.x, startPoint.y, endPoint.x, endPoint.y, true, chosenColor,
                                    chosenLineThickness));
                            setDrawMode(false, false, false, false);
                        }
                        repaint();
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isSelecting) {
                    selectionEnd = limitPointToImageBounds(e.getPoint());
                    repaint();
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    /**
     * <p>
     * Gets the image to be displayed.
     * </p>
     * 
     * @return the image being used.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current scale of the image.
     * </p>
     * 
     * @return The current scale
     */
    public double getZoom() {
        return 100 * scale;
    }

    /**
     * <p>
     * Set the current scale of the image.
     * </p>
     * 
     * @param zoomPercent The amount to be zoomed in by. 
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }

    /**
     * <p>
     * Get preferred size of the image panel.
     * </p>
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth() * scale),
                    (int) Math.round(image.getCurrentImage().getHeight() * scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * Paint the image panel
     * </p>
     * @param g The panel being drawn to.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
            g2.scale(1 / scale, 1 / scale);
            if (cropMode) {
                drawSelectionRectangle(g2);
            }
            if (drawMode && (rectangleMode || ovalMode)) {
                drawSelectionShape(g2);
            }
            if (lineMode) {
                drawLine(g2);
            }
            g2.dispose();
        }
    }

    /**
     * <p>
     * Draw the selection rectangle for crop mode
     * </p>
     * 
     * @param g2 The graphic being drawn to.
     */
    private void drawSelectionRectangle(Graphics2D g2) {
        if (selectionStart != null && selectionEnd != null) {
            int x = Math.min(selectionStart.x, selectionEnd.x);
            int y = Math.min(selectionStart.y, selectionEnd.y);
            int width = Math.abs(selectionStart.x - selectionEnd.x);
            int height = Math.abs(selectionStart.y - selectionEnd.y);
            g2.setColor(new Color(0, 0, 0, 128));
            Area fullArea = new Area(new Rectangle(0, 0, getWidth(), getHeight()));
            Area selectedArea = new Area(new Rectangle(x, y, width, height));
            fullArea.subtract(selectedArea);
            g2.fill(fullArea);
            Stroke stroke = cropMode
                    ? new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f },
                            0.0f)
                    : new BasicStroke(2);
            g2.setStroke(stroke);
            g2.setColor(Color.WHITE);
            g2.drawRect(x, y, width, height);
        }
    }

    /**
     * Draw the selection shape for draw mode
     * 
     * @param g2
     */
    private void drawSelectionShape(Graphics2D g2) {
        if (selectionStart != null && selectionEnd != null) {
            Shape selectionShape;
            if (rectangleMode) {
                selectionShape = getSelectionRectangle();
            } else {
                int x = Math.min(selectionStart.x, selectionEnd.x);
                int y = Math.min(selectionStart.y, selectionEnd.y);
                int width = Math.abs(selectionStart.x - selectionEnd.x);
                int height = Math.abs(selectionStart.y - selectionEnd.y);
                selectionShape = new Ellipse2D.Double(x, y, width, height);
            }
            // Calculate the scaled line width based on zoom factor and line thickness
            float scaledLineWidth = (float) (lineThickness * scale);
            if (fillShapes) {
                // Disable line thickness if the shape is filled
                g2.setStroke(new BasicStroke(1)); // Set line thickness to 1
                g2.setColor(color);
                g2.fill(selectionShape);
            } else {
                g2.setStroke(new BasicStroke(scaledLineWidth));
                g2.setColor(color);
                g2.draw(selectionShape);
            }
        }
    }

    /**
     * <p>
     * Draw the line for line mode
     * </p>
     * 
     * @param g2
     */
    private void drawLine(Graphics2D g2) {
        if (selectionStart != null && selectionEnd != null) {
            Point startPoint = getSelectionStartPoint();
            Point endPoint = getSelectionEndPoint();
            // Calculate the scaled line coordinates
            int scaledStartX = (int) (startPoint.x * scale);
            int scaledStartY = (int) (startPoint.y * scale);
            int scaledEndX = (int) (endPoint.x * scale);
            int scaledEndY = (int) (endPoint.y * scale);
            // Calculate the scaled line width based on zoom factor
            float scaledLineWidth = (float) (lineThickness * scale);
            g2.setStroke(new BasicStroke(scaledLineWidth));
            g2.setColor(color);
            g2.drawLine(scaledStartX, scaledStartY, scaledEndX, scaledEndY);
        }
    }

    /**
     * <p>
     * Get the selection rectangle for draw mode
     * </p>
     * 
     * @return The selection area
     */
    public Rectangle getSelectionRectangle() {
        if (selectionStart == null || selectionEnd == null) {
            return null;
        }
        int x = Math.min(selectionStart.x, selectionEnd.x);
        int y = Math.min(selectionStart.y, selectionEnd.y);
        int width = Math.abs(selectionStart.x - selectionEnd.x);
        int height = Math.abs(selectionStart.y - selectionEnd.y);
        return new Rectangle(x, y, width, height);
    }

    /**
     * <p>
     * Reset the selection for crop mode.
     * </p>
     */
    public void resetSelection() {
        selectionStart = null;
        selectionEnd = null;
        repaint();
    }

    /**
     * <p>
     * Checks if the given point is within the bounds of the image
     * </p>
     * 
     * @param p The point to check
     * @return True if the point is within the bounds of the image, false otherwise
     */
    public boolean isPointInImageBounds(Point p) {
        if (image.hasImage()) {
            int imageWidth = (int) Math.round(image.getCurrentImage().getWidth() * scale);
            int imageHeight = (int) Math.round(image.getCurrentImage().getHeight() * scale);
            return p.x >= 0 && p.x <= imageWidth && p.y >= 0 && p.y <= imageHeight;
        }
        return false;
    }

    /**
     * <p>
     * Limits the given point to the bounds of the image
     * </p>
     * 
     * @param p The point to limit
     * @return The limited point
     */
    public Point limitPointToImageBounds(Point p) {
        if (image.hasImage()) {
            int imageWidth = (int) Math.round(image.getCurrentImage().getWidth() * scale);
            int imageHeight = (int) Math.round(image.getCurrentImage().getHeight() * scale);
            return new Point(Math.max(0, Math.min(p.x, imageWidth)), Math.max(0, Math.min(p.y, imageHeight)));
        }
        return p;
    }

    /**
     * <p>
     * Returns true if the crop mode is enabled, false otherwise
     * </p>
     * 
     * @return If cropMode is enabled or not
     */
    public boolean getCropMode() {
        return cropMode;
    }

    /**
     * <p>
     * Sets the crop mode
     * </p>
     * 
     * @param cropMode Whether cropMode will be enabled or not
     */
    public void setCropMode(boolean cropMode) {
        this.cropMode = cropMode;
        if (cropMode) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * <p>
     * Returns true if the draw mode is enabled, false otherwise.
     * </p>
     * 
     * @return Whether drawMode is enabled or not
     */
    public boolean getDrawMode() {
        return drawMode;
    }

    /**
     * <p>
     * Sets the draw mode
     * </p>
     * 
     * @param drawMode
     * @param rectangleMode
     * @param ovalMode
     * @param lineMode
     */
    public void setDrawMode(boolean drawMode, boolean rectangleMode, boolean ovalMode, boolean lineMode) {
        this.drawMode = drawMode;
        this.rectangleMode = rectangleMode;
        this.ovalMode = ovalMode;
        this.lineMode = lineMode;
        fillShapes = false;
        resetSelection();
        setCropMode(false);
        if (drawMode || lineMode) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * <p>
     * Returns the selection start point
     * </p>
     * 
     * @return The point the selection starts, otherwise null.
     */
    public Point getSelectionStartPoint() {
        if (selectionStart != null) {
            return new Point((int) (selectionStart.x / scale), (int) (selectionStart.y / scale));
        }
        return null;
    }

    /**
     * <p>
     * Returns the selection end point.
     * </p>
     * 
     * @return The point the selection ends, otherwise null.
     */
    public Point getSelectionEndPoint() {
        if (selectionEnd != null) {
            return new Point((int) (selectionEnd.x / scale), (int) (selectionEnd.y / scale));
        }
        return null;
    }

    /**
     * <p>
     * Set the color for drawing
     * </p>
     * 
     * @param color Chosen color.
     */
    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    /**
     * <p>
     * Returns the image panel.
     * </p>
     */
    public ImagePanel getImagePanel() {
        return this;
    }

    /**
     * <p>
     * Set the option to fill shapes.
     * </p>
     * 
     * @param fillShapes Sets if fill is true or false.
     */
    public void setFillShapes(boolean fillShapes) {
        this.fillShapes = fillShapes;
    }

    /**
     * <p>
     * Returns true if the shapes should be filled, false otherwise.
     * </p>
     * 
     * @return The current value of fillShapes.
     */
    public boolean getFillShapes() {
        return fillShapes;
    }

    /**
     * <p>
     * Returns the current line thickness.
     * </p>
     * 
     * @return The current value of lineThickness.
     */
    public float getLineThickness() {
        return lineThickness;
    }


    /**
     * <p>
     * Sets the line thickness.
     * </p>
     * 
     * @param lineThickness The new line thickness.
     */
    public void setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
        repaint();
    }

}
