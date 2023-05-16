package cosc202.andie;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

/**
 * UI display element for EditableImages.
 * This class extends JPanel to allow for rendering of an image, as well as zooming in and out.
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

    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
        isSelecting = false;
        cropMode = false;
        drawMode = false;
        lineMode = false;
        rectangleMode = false;
        ovalMode = false;
        color = Color.RED;
    
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
                                    (int) (selectionRectangle.height / scale)
                            );
                            image.apply(new Crop(cropRectangle, getZoom() / 100, 0, 0));
                            resetSelection();
                            setCropMode(false);
                        }
                        if (drawMode && (rectangleMode || ovalMode) && getSelectionRectangle() != null) {
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
                            image.apply(new Draw(selectionShape, getZoom() / 100, 0, 0, rectangleMode, ovalMode,
                                    false, 0, 0, 0, 0));
                            setDrawMode(false, false, false, false);
                        }
                        if (lineMode && getSelectionStartPoint() != null && getSelectionEndPoint() != null) {
                            Point startPoint = new Point(
                                    (int) (getSelectionStartPoint().x / scale),
                                    (int) (getSelectionStartPoint().y / scale)
                            );
                            Point endPoint = new Point(
                                    (int) (getSelectionEndPoint().x / scale),
                                    (int) (getSelectionEndPoint().y / scale)
                            );
                            image.apply(new Draw(null, getZoom() / 100, 0, 0, false, false,
                                    true, startPoint.x, startPoint.y, endPoint.x, endPoint.y));
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
    
    
    public EditableImage getImage() {
        return image;
    }
    
    public double getZoom() {
        return 100 * scale;
    }
    
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }
    
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth() * scale),
                    (int) Math.round(image.getCurrentImage().getHeight() * scale));
        } else {
            return new Dimension(450, 450);
        }
    }
    
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
    
            Stroke stroke = cropMode ? new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f) : new BasicStroke(2);
            g2.setStroke(stroke);
            g2.setColor(Color.WHITE);
            g2.drawRect(x, y, width, height);
        }
    }
    
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
    
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.RED);
            g2.draw(selectionShape);
        }
    }
    
    
    private void drawLine(Graphics2D g2) {
        if (selectionStart != null && selectionEnd != null) {
            Point startPoint = getSelectionStartPoint();
            Point endPoint = getSelectionEndPoint();
            g2.setStroke(new BasicStroke(2));
            g2.setColor(color);
            g2.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
    }

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
    
    
    public void resetSelection() {
        selectionStart = null;
        selectionEnd = null;
        repaint();
    }
    
    public boolean isPointInImageBounds(Point p) {
        if (image.hasImage()) {
            int imageWidth = (int) Math.round(image.getCurrentImage().getWidth() * scale);
            int imageHeight = (int) Math.round(image.getCurrentImage().getHeight() * scale);
            return p.x >= 0 && p.x <= imageWidth && p.y >= 0 && p.y <= imageHeight;
        }
        return false;
    }
    
    public Point limitPointToImageBounds(Point p) {
        if (image.hasImage()) {
            int imageWidth = (int) Math.round(image.getCurrentImage().getWidth() * scale);
            int imageHeight = (int) Math.round(image.getCurrentImage().getHeight() * scale);
            return new Point(Math.max(0, Math.min(p.x, imageWidth)), Math.max(0, Math.min(p.y, imageHeight)));
        }
        return p;
    }
    
    public boolean getCropMode() {
        return cropMode;
    }
    
    public void setCropMode(boolean cropMode) {
        this.cropMode = cropMode;
        if (cropMode) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    public boolean getDrawMode() {
        return drawMode;
    }
    
    public void setDrawMode(boolean drawMode, boolean rectangleMode, boolean ovalMode, boolean lineMode) {
        this.drawMode = drawMode;
        this.rectangleMode = rectangleMode;
        this.ovalMode = ovalMode;
        this.lineMode = lineMode;
        if (drawMode || lineMode) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    public Point getSelectionStartPoint() {
        if (selectionStart != null) {
            return new Point((int) (selectionStart.x / scale), (int) (selectionStart.y / scale));
        }
        return null;
    }
    
    public Point getSelectionEndPoint() {
        if (selectionEnd != null) {
            return new Point((int) (selectionEnd.x / scale), (int) (selectionEnd.y / scale));
        }
        return null;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }


	public ImagePanel getImagePanel() {
        return this;
    }
	
}
    
    

