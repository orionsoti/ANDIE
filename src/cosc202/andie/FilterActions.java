package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

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
        actions.add(new MeanFilterAction(LanguageSettings.getTranslated("meanFilter"), null, LanguageSettings.getTranslated("meanDesc"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SharpenFilterAction(LanguageSettings.getTranslated("sharpenFilter"), null, LanguageSettings.getTranslated("sharpenDesc"), Integer.valueOf(KeyEvent.VK_N)));
        actions.add(new GaussianBlurFilterAction(LanguageSettings.getTranslated("gaussianBlurFilter"), null, LanguageSettings.getTranslated("gaussianDesc"), Integer.valueOf(KeyEvent.VK_U)));
        actions.add(new MedianFilterAction(LanguageSettings.getTranslated("medianFilter"), null, LanguageSettings.getTranslated("medianDesc"), Integer.valueOf(KeyEvent.VK_N)));
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

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, LanguageSettings.getTranslated("radiusAsk"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            try{
                target.getImage().apply(new MeanFilter(radius));
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

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 5, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, LanguageSettings.getTranslated("radiusAsk"), 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            try{
                target.getImage().apply(new MedianFilter(radius));
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
         * {@link GaussianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         * @throws NullPointerException If there is no image loaded.
         */
        public void actionPerformed(ActionEvent e) {
            // Determine the radius - ask the user.
            int radius = 1;
            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, LanguageSettings.getTranslated("radiusAsk"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);
            // Returns early if the user cancels the operation.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }else if(option ==JOptionPane.OK_OPTION){
                radius = radiusModel.getNumber().intValue();
            }
            // Create and apply the filter
            try{
                target.getImage().apply(new GaussianBlurFilter(radius));
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

