package cosc202.andie;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import javax.swing.*;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well as zooming
 * in and out. 
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {
    
    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally as a percentage.
     * </p>
     */
    private double scale;

    // start and end points of the current selection
    private Point selectionStart;
    private Point selectionEnd;

    // true if the user is currently selecting a region
    private boolean isSelecting;

    // true if the user is currently cropping
    private boolean cropMode;


    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
        isSelecting = false;

        /**
         * <p>
         * Add a mouse listener to the panel to allow for selection of a region of the image.
         * </p>
         */
        MouseAdapter mouseAdapter = new MouseAdapter() {
            /**
             * <p>
             * When the mouse is clicked, the selection is reset.
             * </p>
             * 
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && cropMode) {
                    isSelecting = true;
                    selectionStart = e.getPoint();
                    selectionEnd = e.getPoint();
                }
            }
        
            /**
             * <p>
             * When the mouse is released, the selection is complete.
             * </p>
             * 
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && cropMode) {
                    isSelecting = false;
                    if (selectionStart.equals(selectionEnd)){
                        selectionStart = null;
                        selectionEnd = null;
                    }
                    if (getSelectionRectangle() != null) {
                        image.apply(new Crop(getSelectionRectangle(), getZoom()/100, 0, 0));
                        resetSelection();
                        setCropMode(false);
                    }
                    repaint();
                }
            }

            /**
             * <p>
             * Update the selection rectangle as the mouse is dragged.
             * </p>
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isSelecting) {
                    selectionEnd = e.getPoint();
                    repaint();
                }
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);


    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * New method added to get the image panel to be used for cropping 
     * </p>
     * @return
     */
    public ImagePanel getImagePanel(){
        return this;
        

    }
    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100*scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * @param zoomPercent The new zoom level as a percentage.
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
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth()*scale), 
                                 (int) Math.round(image.getCurrentImage().getHeight()*scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2  = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
            g2.scale(1 / scale, 1 / scale); // Reset the scale for drawing the selection rectangle
            drawSelectionRectangle(g2);
            g2.dispose();
        }
    }

    /** 
     * <p>
     * Draw the selection rectangle on the image.
     * </p>
     * 
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
            if (cropMode){
                float[] dash = {10.0f};
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            }else{
                g2.setStroke(new BasicStroke(2));
            }
            g2.setColor(Color.YELLOW);
            //g2.setStroke(new BasicStroke(2));
            g2.drawRect(x, y, width, height);
        }
    }

    /**
     * <p>
     * Get the current selection rectangle.
     * </p>
     * 
     * @return The current selection rectangle, or null if no selection is present.
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
     * Reset the selection rectangle.
     * </p>
     */
    public void resetSelection() {
        selectionStart = null;
        selectionEnd = null;
        repaint();
    }

    public boolean getCropMode(){
        return cropMode;
    }

    public void setCropMode(boolean cropMode){
        this.cropMode = cropMode;
        if (cropMode){
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }else{
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    
    
}
