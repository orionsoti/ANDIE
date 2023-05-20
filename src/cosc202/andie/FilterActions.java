package cosc202.andie;

import java.util.*;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.event.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood. 
 * This includes a mean filter, median filter, sharpen filter and gaussian blur filter.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FilterActions {
    
    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new SharpenFilterAction(LanguageSettings.getTranslated("sharpenFilter"), new ImageIcon("src/images/sharpen.png"), LanguageSettings.getTranslated("sharpenDesc"), Integer.valueOf(KeyEvent.VK_N)));
        actions.add(new MeanFilterAction(LanguageSettings.getTranslated("meanFilter"), new ImageIcon("src/images/blur.png"), LanguageSettings.getTranslated("meanDesc"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new GaussianBlurFilterAction(LanguageSettings.getTranslated("gaussianBlurFilter"), new ImageIcon("src/images/blur.png"), LanguageSettings.getTranslated("gaussianDesc"), Integer.valueOf(KeyEvent.VK_U)));
        actions.add(new MedianFilterAction(LanguageSettings.getTranslated("medianFilter"), new ImageIcon("src/images/blur.png"), LanguageSettings.getTranslated("medianDesc"), Integer.valueOf(KeyEvent.VK_N)));
        actions.add(new EmbossFilterAction(LanguageSettings.getTranslated("embossFilter"), new ImageIcon("src/images/emboss-sobel.png"), LanguageSettings.getTranslated("embossDesc"), Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new SobelFilterAction(LanguageSettings.getTranslated("sobelFilter"), new ImageIcon("src/images/emboss-sobel.png"), LanguageSettings.getTranslated("sobelDesc"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new MatrixFilterAction(LanguageSettings.getTranslated("matrixFilter"), new ImageIcon("src/images/matrix.png"), LanguageSettings.getTranslated("matrixDesc"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new PixelateFilterAction(LanguageSettings.getTranslated("pixelateFilter"), new ImageIcon("src/images/pixel.png"), LanguageSettings.getTranslated("pixelateDesc"), Integer.valueOf(KeyEvent.VK_P), 80, 80)); 

        
    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageSettings.getTranslated("filter"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the meanFilterAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         * @throws NullPointerException If there is no image loaded.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            BufferedImage original = target.getImage().getCurrentImage();

            // Pop-up dialog box to ask for the intensity value of the contrast.
            DefaultBoundedRangeModel radiusModel = new DefaultBoundedRangeModel(0, 0, 0, 10);
            JSlider radiusSlider = new JSlider(radiusModel);
            radiusSlider.setPreferredSize(new Dimension(300, 50));
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setSnapToTicks(enabled);

            radiusSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    target.getImage().setCurrentImage(original);
                    int radius = radiusSlider.getValue();
                    
                    // Create and apply the filter.
                    try{
                        target.getImage().preview(new MeanFilter(radius));
                        target.repaint();
                        target.getParent().revalidate();
                    }catch(NullPointerException exception){
                        Object[] options = {LanguageSettings.getTranslated("ok")};
                        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                    }
                }
            });

            // Pop-up dialog box to ask for the radius value.
            radiusSlider.setPaintLabels(true);
            int option = JOptionPane.showOptionDialog(null, radiusSlider, LanguageSettings.getTranslated("radiusAsk"),
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);

            // Check the return value from the dialog box.
            if (option == 1) {
                target.getImage().setCurrentImage(original);
                target.repaint();
                return;
            } 
            else if (option == JOptionPane.OK_OPTION) {
                int radius = radiusSlider.getValue();
                target.getImage().setCurrentImage(original);
                target.getImage().apply(new MedianFilter(radius));
                return;
            }
            
        }

    }
    
    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the medianFilterAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         * @throws NullPointerException If there is no image loaded.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            BufferedImage original = target.getImage().getCurrentImage();

            // Pop-up dialog box to ask for the intensity value of the contrast.
            DefaultBoundedRangeModel radiusModel = new DefaultBoundedRangeModel(0, 0, 0, 5);
            JSlider radiusSlider = new JSlider(radiusModel);
            radiusSlider.setPreferredSize(new Dimension(300, 50));
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setSnapToTicks(enabled);

            radiusSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    target.getImage().setCurrentImage(original);
                    int radius = radiusSlider.getValue();
                    
                    // Create and apply the filter.
                    try{
                        target.getImage().preview(new MedianFilter(radius));
                        target.repaint();
                        target.getParent().revalidate();
                    }catch(NullPointerException exception){
                        Object[] options = {LanguageSettings.getTranslated("ok")};
                        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                    }
                }
            });

            // Pop-up dialog box to ask for the radius value
            radiusSlider.setPaintLabels(true);
            int option = JOptionPane.showOptionDialog(null, radiusSlider, LanguageSettings.getTranslated("radiusAsk"),
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);

            // Check the return value from the dialog box.
            if (option == 1) {
                target.getImage().setCurrentImage(original);
                target.repaint();
                return;
            } 
            else if (option == JOptionPane.OK_OPTION) {
                int radius = radiusSlider.getValue();
                target.getImage().setCurrentImage(original);
                target.getImage().apply(new MedianFilter(radius));
                return;
            }
        }

    }

    /**
     * <p>
     * Action to sharpen an image with a sharpen filter.
     * </p>
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction{
        /**
         * <p>
         * Create a new sharpen-filter action.
         * <p>
         * 
         * @param name The name of the action
         * @param icon An icon to used to represent the action
         * @param desc A brief description of the action
         * @param mnemonic A mnemonic key to use as a shortcut
         * @param accelerator An accelerator key to use as a shortcut
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the sharpenFilterAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         * @throws NullPointerException If there is no image loaded.
         */
        public void actionPerformed(ActionEvent e){
            if (!target.getImage().hasImage()) {
                return;
            }
            // Pop-up dialog box to confirm user wishes to apply filter.
            JLabel text = new JLabel(LanguageSettings.getTranslated("sharpenAsk"));
            int option = JOptionPane.showOptionDialog(target.getParent(), text, LanguageSettings.getTranslated("sharpenAsk"),JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            // Create and apply the filter
            try{
                target.getImage().apply(new SharpenFilter());
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
     * Gaussian blur filter action.
     * </p>
     */
    public class GaussianBlurFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new GaussianBlurFilter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussianBlurFilterAction(String name, ImageIcon iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianBlurFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link GaussianBlurFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         * @throws NullPointerException If there is no image loaded.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            BufferedImage original = target.getImage().getCurrentImage();

            // Pop-up dialog box to ask for the radius value.
            DefaultBoundedRangeModel radiusModel = new DefaultBoundedRangeModel(1, 0, 1, 10);
            JSlider radiusSlider = new JSlider(radiusModel);
            radiusSlider.setPreferredSize(new Dimension(300, 50));
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setSnapToTicks(enabled);

            radiusSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    target.getImage().setCurrentImage(original);
                    int radius = radiusSlider.getValue();
                    
                    // Create and apply the filter.
                    try{
                        target.getImage().preview(new GaussianBlurFilter(radius));
                        target.repaint();
                        target.getParent().revalidate();
                    }catch(NullPointerException exception){
                        Object[] options = {LanguageSettings.getTranslated("ok")};
                        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                    }
                }
            });

            radiusSlider.setPaintLabels(true);
            int option = JOptionPane.showOptionDialog(null, radiusSlider, LanguageSettings.getTranslated("radiusAsk"),
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);
            
            // Returns early if the user cancels the operation.
            if (option == 1) {
                target.getImage().setCurrentImage(original);
                target.repaint();
                return;
            }else if(option ==JOptionPane.OK_OPTION){
                int radius = radiusSlider.getValue();
                target.getImage().setCurrentImage(original);
                target.getImage().apply(new GaussianBlurFilter(radius));
                return;
            }
        }

    }
    
    /**
     * <p>
     * Emboss filter action.
     * </p>
     * 
     */
    public class EmbossFilterAction extends ImageAction {
        /**
         * <p>
         * Create a new EmbossFilter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }
        
        /**
         * <p>
         * Callback for when the embossFilterAction is triggered.
         * </p>
         * 
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            
            String[] directions = {LanguageSettings.getTranslated("northwest"), LanguageSettings.getTranslated("north"), LanguageSettings.getTranslated("northeast"), LanguageSettings.getTranslated("west"), LanguageSettings.getTranslated("east"), 
                LanguageSettings.getTranslated("southwest"), LanguageSettings.getTranslated("south"), LanguageSettings.getTranslated("southeast")};
            
            JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
            ButtonGroup buttonGroup = new ButtonGroup();
            int[] buttonValues = {EmbossFilter.NORTHWEST, EmbossFilter.NORTH, EmbossFilter.NORTHEAST, EmbossFilter.WEST, EmbossFilter.EAST, EmbossFilter.SOUTHWEST, EmbossFilter.SOUTH, EmbossFilter.SOUTHEAST};
            
            for (int i = 0; i < directions.length; i++) {
                JToggleButton button = new JToggleButton(directions[i]);
                button.setActionCommand(String.valueOf(buttonValues[i]));
                buttonGroup.add(button);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int direction = Integer.parseInt(((AbstractButton) e.getSource()).getActionCommand());
                        try {
                            target.getImage().apply(new EmbossFilter(direction));
                            target.repaint();
                            target.getParent().revalidate();
                            Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                            window.dispose(); // Close the dialog
                        } catch (NullPointerException exception) {
                            Object[] options = {LanguageSettings.getTranslated("ok")};
                            JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        }
                    }
                });
                buttonPanel.add(button);
                button.setFocusPainted(false);
            }
            
            buttonPanel.add(new JLabel(), 4); // Add an empty label in the center of the grid
            
            JOptionPane.showOptionDialog(null, buttonPanel, LanguageSettings.getTranslated("selectDirection"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{}, null);
        }
        
        
    }

    /**
     * <p>
     * Sobel filter action.
     */
    public class SobelFilterAction extends ImageAction {
        /**
         * <p>
         * Create a new SobelFilter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SobelFilterAction(String name, ImageIcon iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }
        
        /**
         * <p>
         * Callback for when the SobelFilter action is triggered.
         * </p>
         * 
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
        
            String[] directions = {LanguageSettings.getTranslated("horizontal"), LanguageSettings.getTranslated("vertical")};
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
            ButtonGroup buttonGroup = new ButtonGroup();
            boolean[] buttonValues = {SobelFilter.HORIZONTAL, SobelFilter.VERTICAL};
        
            for (int i = 0; i < directions.length; i++) {
                JToggleButton button = new JToggleButton(directions[i]);
                button.setActionCommand(String.valueOf(buttonValues[i]));
                buttonGroup.add(button);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boolean direction = Boolean.parseBoolean(((AbstractButton) e.getSource()).getActionCommand());
                        try {
                            target.getImage().apply(new SobelFilter(direction));
                            target.repaint();
                            target.getParent().revalidate();
                            Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                            window.dispose(); // Close the dialog
                        } catch (NullPointerException exception) {
                            Object[] options = {LanguageSettings.getTranslated("ok")};
                            JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        }
                    }
                });
                button.setFocusPainted(false);
                buttonPanel.add(button);
            }
        
            JOptionPane.showOptionDialog(null, buttonPanel, LanguageSettings.getTranslated("selectDirection"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{}, null);
        }
        
        
    }

    public class MatrixFilterAction extends ImageAction{
        /**
         * <p>
         * Create a new MatrixFilter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MatrixFilterAction(String name, ImageIcon iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            if (!target.getImage().hasImage()) {
                return;
            }
            // Pop-up dialog box to confirm user wishes to apply filter.
            JLabel text = new JLabel("Apply matrix filter?");
            int option = JOptionPane.showOptionDialog(target.getParent(), text, LanguageSettings.getTranslated("matrixAsk"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            // Create and apply the filter
            try{
                target.getImage().apply(new MatrixFilter());
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
     * Pixel art filter action.
     * </p>
     * 
     */
    public class PixelateFilterAction extends ImageAction{
        private int newWidth;
        private int newHeight;
        
        /**
         * <p>
         * Create a new PixelArtFilter action.
         * </p>
         * 
         * @param name
         * @param iconName
         * @param desc
         * @param mnemonic
         * @param newWidth
         * @param newHeight
         */
        PixelateFilterAction(String name, ImageIcon iconName, String desc, Integer mnemonic, int newWidth, int newHeight) {
            super(name, iconName, desc, mnemonic);
            this.newWidth = newWidth;
            this.newHeight = newHeight;
        }
        
        /**
         * <p>
         * Callback for when the PixelArtFilter action is triggered.
         * </p>
         * 
         */
        public void actionPerformed(ActionEvent e){
            if (!target.getImage().hasImage()) {
                return;
            }
            // Pop-up dialog box to confirm user wishes to apply filter.
            JLabel text = new JLabel(LanguageSettings.getTranslated("pixelateAsk"));
            int option = JOptionPane.showOptionDialog(target.getParent(), text, LanguageSettings.getTranslated("pixelateAsk"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                (Icon) getValue(Action.LARGE_ICON_KEY),new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            try{
                target.getImage().apply(new PixelateFilter(newWidth, newHeight));
                target.repaint();
                target.getParent().revalidate();
            }catch(NullPointerException exception){
                Object[] options = {LanguageSettings.getTranslated("ok")};
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"), LanguageSettings.getTranslated("alert"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
            }
        }
    }
    
}



  

