package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {
    
    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction(LanguageSettings.getTranslated("greyScale"), null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new ContrastAdjustAction(LanguageSettings.getTranslated("contrast"), null, "Adjust the contrast", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new BrightnessAdjustAction(LanguageSettings.getTranslated("brightness"), null, "Adjust the brightness", Integer.valueOf(KeyEvent.VK_G)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageSettings.getTranslated("colour"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            }catch(NullPointerException exception){
                JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("noInput"));
            }
        }

    }
     /**
     * <p>
     * Action to adjust the contrast of an image.
     * </p>
     * 
     * @see ContrastBrightnessAdjust
     */
    public class ContrastAdjustAction extends ImageAction {

        /**
         * <p>
         * Create a new contrast adjustment action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ContrastAdjustAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        /**
         * <p>
         * Callback for when the ContrastAdjustAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ContrastAdjustAction is triggered.
         * It opens a JSlider window that allows the user to adjust the image's contrast in between -100 and 100.
         * Once contrast has been selected will create an instance of ContrastBrightnessAdjust passing the value to the contrast parameter
         * and 0 to the brightness parameter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
                        int intensity = 0;
                        // Pop-up dialog box to ask for the intensity value of the contrast.
                        DefaultBoundedRangeModel intensityModel = new DefaultBoundedRangeModel(0, 0, -100, 100);
                        JSlider intensitySlider = new JSlider(intensityModel);
                        Hashtable<Integer, JComponent> sliderLabels = intensitySlider.createStandardLabels(25, -100); //Create the labels for the slider
                        intensitySlider.setLabelTable(sliderLabels);
                        intensitySlider.setPaintLabels(true);
                        ImageIcon contrastIcon = new ImageIcon("src/contrast.png", "contrast icon");
                        int option = JOptionPane.showOptionDialog(null, intensitySlider, "Contrast Intensity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, contrastIcon, null, null);
            
                        // Check the return value from the dialog box.
                        if (option == JOptionPane.CANCEL_OPTION) {
                            return;
                        } else if (option == JOptionPane.OK_OPTION) {
                            intensity = intensityModel.getValue();
                        }
            
                        // Create and apply the filter.
                        try{
                            target.getImage().apply(new ContrastBrightnessAdjust(intensity,0));
                            target.repaint();
                            target.getParent().revalidate();
                        }catch(NullPointerException exception){
                            JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("noInput"));
                        }
                    
        }

    }
     /**
     * <p>
     * Action to adjust the brightness of an image.
     * </p>
     * 
     * @see ContrastBrightnessAdjust
     */

    public class BrightnessAdjustAction extends ImageAction {

        /**
         * <p>
         * Create a new brightness adjustment action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        BrightnessAdjustAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        /**
         * <p>
         * Callback for when the brightnessAdjustAction action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the brightnessAdjustAction is triggered.
         * It opens a JSlider window that allows the user to adjust the image's brightness in between -100 and 100.
         * Once brightness has been selected the method will create an instance of ContrastBrightnessAdjust passing the value to the brightness parameter
         * and 0 to the contrast parameter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
                        // Determine the radius - ask the user.
                        int brightness = 0;

                        // Pop-up dialog box to ask for the intensity value of the brightness.
                        DefaultBoundedRangeModel intensityModel = new DefaultBoundedRangeModel(0, 0, -100, 100);
                        JSlider intensitySlider = new JSlider(intensityModel);
                        Hashtable<Integer, JComponent> sliderLabels = intensitySlider.createStandardLabels(25, -100); // create the labels for the slider
                        intensitySlider.setLabelTable(sliderLabels);
                        intensitySlider.setPaintLabels(true);
                        ImageIcon brightnessIcon = new ImageIcon("src/Brightness.png", "Brightness Icon");
                        int option = JOptionPane.showOptionDialog(null, intensitySlider, "Brightness Intensity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, brightnessIcon, null, null);
            
                        // Check the return value from the dialog box.
                        if (option == JOptionPane.CANCEL_OPTION) {
                            return;
                        } else if (option == JOptionPane.OK_OPTION) {
                            brightness = intensityModel.getValue();
                        }
            
                        // Create and apply the filter
                        try{
                            target.getImage().apply(new ContrastBrightnessAdjust(0,brightness));
                            target.repaint();
                            target.getParent().revalidate();
                        }catch(NullPointerException exception){
                            JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("noInput"));
                        }
                    
        }

    }

}
