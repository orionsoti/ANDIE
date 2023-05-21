package cosc202.andie;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Toolkit;

/**
 * <p>
 * Actions provided by the Transform
 * </p>
 * 
 * <p>
 * The Transform menu contains actions such as resize, rotate, and flip that affect the contents of the image.
 * </p>
 * 
 * @author Orion Soti Jacob Myron
 * @version 1.0
 * 2 April 2023
 * 
 */
public class TransformActions {
    /**
    * A list of actions for the Transform menu.
    */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Transform menu actions.
     * </p>
     * 
     */
    public TransformActions(){
        actions = new ArrayList<Action>();
        actions.add(new ResizeAction(LanguageSettings.getTranslated("resize"), new ImageIcon("src/images/resize_small.png"), LanguageSettings.getTranslated("resizeDesc"), null));
        actions.add(new ImageRotationAction(LanguageSettings.getTranslated("rotateLeft"), new ImageIcon("src/images/rotate-left_small.png"), LanguageSettings.getTranslated("rotateLeftDesc"), Integer.valueOf(KeyEvent.VK_L), 2));
        actions.add(new ImageRotationAction(LanguageSettings.getTranslated("rotateRight"), new ImageIcon("src/images/rotate-right_small.png"), LanguageSettings.getTranslated("rotateRightDesc"), Integer.valueOf(KeyEvent.VK_R), 1));
        actions.add(new FlipAction(LanguageSettings.getTranslated("flipVertical"), new ImageIcon("src/images/flip-v_small.png"), LanguageSettings.getTranslated("flipVerticalDesc"), null, Flip.FLIP_VERTICAL));
        actions.add(new FlipAction(LanguageSettings.getTranslated("flipHorizontal"), new ImageIcon("src/images/flip-h_small.png"), LanguageSettings.getTranslated("flipHorizontalDesc"), null, Flip.FLIP_HORIZONTAL));
        actions.add(new CropAction(LanguageSettings.getTranslated("crop"), new ImageIcon("src/images/crop_small.png"), LanguageSettings.getTranslated("cropDesc"), null));
        actions.add(new DrawAction(LanguageSettings.getTranslated("draw"), new ImageIcon("src/images/draw_small.png"), LanguageSettings.getTranslated("drawDesc"), null));


        
    }

    /**
     * <p>
     * Create a menu containing the list of Transform actions.
     * </p>
     * 
     * @return A menu containing all the transform actions.
     */
    public JMenu createMenu(){
        JMenu transformMenu = new JMenu(LanguageSettings.getTranslated("transform"));
        for (Action action: actions) {
            transformMenu.add(new JMenuItem(action));
        }
        return transformMenu;
    }

    /**
     * <p>
     * Creates and adds transform action buttons to the toolBar.
     * </p>
     * 
     * @param toolBar Target JMenuBar that the buttons are added to.
     */
    public void createToolMenu(JMenuBar toolBar){
        //Creates Buttons
        JButton rotateLeft= new JButton(new ImageIcon("src/images/rotate-left_3.png"));
        JButton rotateRight = new JButton(new ImageIcon("src/images/rotate-right_3.png"));
        JButton flipVert = new JButton(new ImageIcon("src/images/flip_vert.png"));
        JButton flipHor = new JButton(new ImageIcon("src/images/flip_hor.png"));
        JButton draw = new JButton(new ImageIcon("src/images/pen.png"));
        JButton crop = new JButton(new ImageIcon("src/images/crop.png"));


        //Adds action Listeners
        rotateLeft.addActionListener(actions.get(1));
        rotateRight.addActionListener(actions.get(2));
        flipVert.addActionListener(actions.get(3));
        flipHor.addActionListener(actions.get(4));
        draw.addActionListener(actions.get(6));
        crop.addActionListener(actions.get(5));

        //Sets the buttons sizes
        rotateLeft.setPreferredSize(Andie.buttonSize);
        rotateRight.setPreferredSize(Andie.buttonSize);
        flipVert.setPreferredSize(Andie.buttonSize);
        flipHor.setPreferredSize(Andie.buttonSize);
        draw.setPreferredSize(Andie.buttonSize);
        crop.setPreferredSize(Andie.buttonSize);

        // Sets the tooltips
        rotateLeft.setToolTipText(LanguageSettings.getTranslated("rotateLeft"));
        rotateRight.setToolTipText(LanguageSettings.getTranslated("rotateRight"));
        flipHor.setToolTipText(LanguageSettings.getTranslated("flipHorizontal"));
        flipVert.setToolTipText(LanguageSettings.getTranslated("flipVertical"));
        draw.setToolTipText(LanguageSettings.getTranslated("draw"));
        crop.setToolTipText(LanguageSettings.getTranslated("crop"));

        rotateLeft.setFocusPainted(false);
        rotateRight.setFocusPainted(false);
        flipVert.setFocusPainted(false);
        flipHor.setFocusPainted(false);
        draw.setFocusPainted(false);
        crop.setFocusPainted(false);

        // Create a separator
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        Dimension separatorDimension = new Dimension(separator.getPreferredSize().width, toolBar.getPreferredSize().height);
        separator.setMaximumSize(separatorDimension);

        // Add the separator to the toolBar
        toolBar.add(separator);

        //Adds the buttons to the toolBar
        toolBar.add(rotateLeft);
        toolBar.add(rotateRight);
        toolBar.add(flipVert);
        toolBar.add(flipHor);
        toolBar.add(crop);
        toolBar.add(draw);
    }

    /**
     * <p>
     * Action to flip an image.
     * </p>
     * 
     * 
     */
    public class FlipAction extends ImageAction{
        private int direction;

        FlipAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        FlipAction(String name, ImageIcon icon, String desc, Integer mnemonic, int direction){
            super(name, icon, desc, mnemonic);
            this.direction = direction;
        }

        /**
         * <p>
         * When button is clicked
         * </p>
         * 
         * @throws NullPointerException If there is no image loaded
         */

        public void actionPerformed(ActionEvent e){
            if (!target.getImage().hasImage()) {
                return;
            }
            // Create and apply the filter
            try{
                target.getImage().apply(new Flip(direction));
                target.repaint();
                target.getParent().revalidate();
            }catch(NullPointerException exception){
                Object[] options = {LanguageSettings.getTranslated("ok")};
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
            }
        }
    }

    /**
     * <p>
     * Action to resize an image.
     * </p>
     * 
     */
    public class ResizeAction extends ImageAction{
        public int height;
        public int width;
        public double scale;

        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }
        
        /**
         * <p>
         * Callback for when the resize action is performed.
         * </p>
         * 
         * @param e The event that triggered the action.
         * @throws NullPointerException If there is no image loaded
         */
        public void actionPerformed(ActionEvent e){
            if (!target.getImage().hasImage()) {
                return;
            }
            try{
                height = target.getImage().getCurrentImage().getHeight();
                width = target.getImage().getCurrentImage().getWidth();
                scale = 1.0;

                // Create a dialog to get the scale factor, width and height from the user
                SpinnerNumberModel scaleSpinner = new SpinnerNumberModel(scale * 100, 0.0, 1000.0, 1);
                SpinnerNumberModel widthSpinner = new SpinnerNumberModel(width, 1, Integer.MAX_VALUE, 1);
                SpinnerNumberModel heightSpinner = new SpinnerNumberModel(height, 1, Integer.MAX_VALUE, 1);
                JSpinner s = new JSpinner(scaleSpinner);
                JSpinner w = new JSpinner(widthSpinner);
                JSpinner h = new JSpinner(heightSpinner);

                JPanel myPanel = new JPanel(new GridLayout(3, 2));
                ImageIcon resizeIcon = new ImageIcon("src/images/resize.png");
                myPanel.add(new JLabel(LanguageSettings.getTranslated("scale")));
                myPanel.add(s);
                myPanel.add(new JLabel(LanguageSettings.getTranslated("width")));
                myPanel.add(w);
                myPanel.add(new JLabel(LanguageSettings.getTranslated("height")));
                myPanel.add(h);

                int option = JOptionPane.showOptionDialog(target.getParent(), myPanel, LanguageSettings.getTranslated("resize"), 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, resizeIcon, new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);
                if (option == JOptionPane.OK_OPTION) {
                    update(scaleSpinner.getNumber().doubleValue()/100, widthSpinner.getNumber().intValue(), heightSpinner.getNumber().intValue());
                }else if (option == JOptionPane.CANCEL_OPTION){
                    return;
                }

                target.getImage().apply(new Resize(height, width, scale));
                target.repaint();
                target.getParent().revalidate();
                
            } catch(NullPointerException exception){
                Object[] options = {LanguageSettings.getTranslated("ok")};
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
            }
        }
        
        /**
         * Updates the values of the height, width, and scale based on the user input.
         * 
         * @param outputScale The scale of the output image.
         * @param outputWidth The width of the output image.
         * @param outputHeight The height of the output image.
         * 
         */
        public void update(double outputScale, int outputWidth, int outputHeight) {
            /**if (outputScale != scale) {
                scale = outputScale;
                width = (int) (scale * width);
                height = (int) (scale * height);
            } else if (outputWidth != width) {
                width = outputWidth;
                scale = (double) width / (double) this.width;
                height = (int) (scale * height);
            } else if (outputHeight != height) {
                height = outputHeight;
                scale = (double) height / (double) this.height;
                width = (int) (scale * width);
            }*/
            if (outputScale != scale){
                scale = outputScale;
                width = (int) (scale * width);
                height = (int) (scale * height);
            }else{
                width = outputWidth;
                height = outputHeight;
                scale = (double) width / (double) this.width;
            }
        }


    }

    /**
     * <p>
     * Action to rotate an image.
     * </p>
     */
    public class ImageRotationAction extends ImageAction {
        int rotation;
        /**
         * <p>
         * Create a new ImageRotation action.
         * Sets the hotkey for image rotation as 'ctrl + r'
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageRotationAction(String name, ImageIcon iconName, String desc, Integer mnemonic, int rotation) {
            super(name, iconName, desc, mnemonic);
            this.rotation = rotation;
           
            // Sets 'ctrl + r' as the hotkey triggering an image-rotation action (right)
            KeyStroke r = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
            putValue(Action.ACCELERATOR_KEY, r);
            
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(r, getValue(Action.NAME));
    
            target.getActionMap().put(getValue(Action.NAME), this);
        }

        /**
         * <p>
         * Callback for when the rotation action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the imageRotationAction is triggered.
         * Whether the image rotates right or left is dependant on the input from
         * the button the user clicks.
         * {@link ImageRotation}.
         * </p>
         * 
         * @param e The event triggering this callback.
         * @throws NullPointerException If there is no image loaded.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            try{
                target.getImage().apply(new ImageRotation(rotation));
                target.repaint();
                target.getParent().revalidate();
            }catch(NullPointerException exception){
                Object[] options = {LanguageSettings.getTranslated("ok")};
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
            }
        }

    }

    /**
     * <p>
     * Action to crop an image.
     * </p>
     *
     */
    public class CropAction extends ImageAction{
        
        /**
         * Create a new Crop action.
         * Sets 'ctrl + shift + c' as the hotkey for crop.
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        CropAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);

            KeyStroke c = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | InputEvent.SHIFT_DOWN_MASK , enabled);
            putValue(Action.ACCELERATOR_KEY, c);
    
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(c, getValue(Action.NAME));
            
            // Add a key binding for the Escape key
            KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
            inputMap.put(esc, "cancelCrop");
    
            Action cancelCropAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Cancel the crop action here
                    ImagePanel imagePanel = target.getImagePanel();
                    imagePanel.setCropMode(false);
                }
            };
    
            target.getActionMap().put("cancelCrop", cancelCropAction);
            target.getActionMap().put(getValue(Action.NAME), this);
        }
            
        /**
         * Callback for when the crop action is performed.
         * @param e The event that triggered the action.
         * @throws NullPointerException If there is no image loaded
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            try {
                ImagePanel imagePanel = target.getImagePanel();
                // If there is no image loaded, display an error message
                if (!imagePanel.getImage().hasImage()){
                    Object[] options = {LanguageSettings.getTranslated("ok")};
                    JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);                
                    return;
                }
                imagePanel.setDrawMode(false, false, false, false);
                imagePanel.setCropMode(true);
            } catch (NullPointerException exception) {
                Object[] options = {LanguageSettings.getTranslated("ok")};
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
        }
    }
    

    /**
     * <p>
     * Action to draw on an image.
     * </p>
     * 
     */
    public class DrawAction extends ImageAction {
        private JCheckBox fillCheckbox;
        private ColourChooser colorChooser;
        private JPanel optionsPanel; // Declare optionsPanel as an instance variable
        private Color color;
        private JSlider thicknessSlider;
        private JLabel thicknessLabel;
        
        /**
         * <p>
         * Create a new Draw action
         * Sets 'ctrl + d' as the hotkey for draw.
         * </p>
         * 
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        DrawAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            fillCheckbox = new JCheckBox(LanguageSettings.getTranslated("fillShapes"));
            fillCheckbox.setSelected(false);
            // Create and initialize the color chooser
            colorChooser = new ColourChooser();

            // Create a panel to hold the fill checkbox and color chooser
            optionsPanel = new JPanel();
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
            optionsPanel.add(colorChooser);
            // Create a slider to control the thickness of the lines
            thicknessSlider = new JSlider(JSlider.HORIZONTAL, 1, 50, 1);
            thicknessSlider.setMajorTickSpacing(5);
            thicknessLabel = new JLabel(LanguageSettings.getTranslated("lineThickness"));
            JPanel thicknessPanel = new JPanel();
            thicknessPanel.setLayout(new BoxLayout(thicknessPanel, BoxLayout.X_AXIS));
            thicknessPanel.add(thicknessLabel);
            thicknessPanel.add(thicknessSlider);
            thicknessPanel.add(fillCheckbox);
            // Add an empty border with desired spacing between the colorChooser and thicknessPanel
            int spacing = 30; // Adjust the value to increase or decrease spacing
            optionsPanel.add(Box.createVerticalStrut(spacing));
            optionsPanel.add(thicknessPanel);
            KeyStroke c = KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()|InputEvent.SHIFT_DOWN_MASK , enabled);
            putValue(Action.ACCELERATOR_KEY, c);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(c, getValue(Action.NAME));
            target.getActionMap().put(getValue(Action.NAME), this);

            // Assign the escape key to cancel the draw action
            KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
            target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "CancelDraw");
            target.getActionMap().put("CancelDraw", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    ImagePanel imagePanel = target.getImagePanel();
                    imagePanel.setDrawMode(false, false, false, false);
                }
            });
        }
    
        /**
         * Callback for when the draw action is performed.
         *
         * @param e The event that triggered the action.
         * @throws NullPointerException If there is no image loaded
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            try {
                ImagePanel imagePanel = target.getImagePanel();
                // Check if there is an image loaded
                if (!imagePanel.getImage().hasImage()){
                    Object[] options = {LanguageSettings.getTranslated("ok")};
                    JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);     
                    return;           
                }
                Object[] drawOptions = {LanguageSettings.getTranslated("rect"), LanguageSettings.getTranslated("oval"), LanguageSettings.getTranslated("line")};
                int selectedOption = JOptionPane.showOptionDialog(null, optionsPanel, LanguageSettings.getTranslated("drawShape"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, drawOptions, null);
                if (selectedOption == 0) {
                    //System.out.println("Rectangle");
                    imagePanel.setDrawMode(true, true, false, false);
                } else if (selectedOption == 1) {
                    //System.out.println("Oval");
                    imagePanel.setDrawMode(true, false, true, false);
                } else if (selectedOption == 2) {
                    //System.out.println("Line");
                    imagePanel.setDrawMode(true, false, false, true);
                }
                boolean fill = fillCheckbox.isSelected();
                imagePanel.setFillShapes(fill);
                color = ColourChooser.colour;
                imagePanel.setColor(color);
                float lineThickness = thicknessSlider.getValue();
                imagePanel.setLineThickness(lineThickness);
            } catch (NullPointerException exception) {
                Object[] options = {LanguageSettings.getTranslated("ok")};
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"),
                        LanguageSettings.getTranslated("alert"), JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
        }
    }
}
     