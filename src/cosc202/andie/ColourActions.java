package cosc202.andie;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
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
        actions.add(new ConvertToGreyAction(LanguageSettings.getTranslated("greyScale"),
                new ImageIcon("src/images/greyscale.png"), LanguageSettings.getTranslated("greyscaleDesc"),
                Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new ContrastAdjustAction(LanguageSettings.getTranslated("contrast"),
                new ImageIcon("src/images/contrast_small.png"), LanguageSettings.getTranslated("contrastDesc"),
                Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new BrightnessAdjustAction(LanguageSettings.getTranslated("brightness"),
                new ImageIcon("src/images/brightness_small.png"), LanguageSettings.getTranslated("brightnessDesc"),
                Integer.valueOf(KeyEvent.VK_G)));
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

        for (Action action : actions) {
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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            // Set the hotkey 'ctrl + g' to trigger a convert to grey action.
            KeyStroke o = KeyStroke.getKeyStroke(KeyEvent.VK_G, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
            putValue(Action.ACCELERATOR_KEY, o);

            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(o, getValue(Action.NAME));

            target.getActionMap().put(getValue(Action.NAME), this);
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
            if (!target.getImage().hasImage()) {
                return;
            }
            try {
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException exception) {
                Object[] options = { LanguageSettings.getTranslated("ok") };
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"),
                        LanguageSettings.getTranslated("alert"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ContrastAdjustAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            // Set the hotkey 'ctrl + shift + b' to trigger a contrastAdjustAction action.
            KeyStroke o = KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | InputEvent.SHIFT_DOWN_MASK, enabled);
            putValue(Action.ACCELERATOR_KEY, o);

            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(o, getValue(Action.NAME));

            target.getActionMap().put(getValue(Action.NAME), this);
        }

        /**
         * <p>
         * Callback for when the ContrastAdjustAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ContrastAdjustAction is triggered.
         * It opens a JSlider window that allows the user to adjust the image's contrast
         * in between -100 and 100.
         * Once contrast has been selected will create an instance of
         * ContrastBrightnessAdjust passing the value to the contrast parameter
         * and 0 to the brightness parameter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            // Pop-up dialog box to ask for the intensity value of the contrast.
            DefaultBoundedRangeModel intensityModel = new DefaultBoundedRangeModel(0, 0, -100, 100);
            JSlider intensitySlider = new JSlider(intensityModel);
            intensitySlider.setPreferredSize(new Dimension(300, 50));
            intensitySlider.setMajorTickSpacing(25);
            intensitySlider.setMinorTickSpacing(5);
            intensitySlider.setPaintTicks(true);
            intensitySlider.setSnapToTicks(enabled);

            // Create a change listener for the slider that updates the image when the
            // slider's value changes
            intensitySlider.addChangeListener(new ChangeListener() {
                int editCounter = 0;

                public void stateChanged(ChangeEvent e) {
                    if (editCounter > 0) {
                        target.getImage().undo();
                        target.getImage().popRedo();
                    }
                    editCounter++;
                    int intensity = intensitySlider.getValue();
                    // Create and apply the filter.
                    try {
                        target.getImage().apply(new ContrastBrightnessAdjust(intensity, 0));
                        target.repaint();
                        target.getParent().revalidate();
                    } catch (NullPointerException exception) {
                        Object[] options = { LanguageSettings.getTranslated("ok") };
                        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"),
                                LanguageSettings.getTranslated("alert"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    }
                }
            });

            intensitySlider.setPaintLabels(true);
            ImageIcon contrastIcon = new ImageIcon("src/images/contrast-1.png", "contrast icon");
            int option = JOptionPane.showOptionDialog(null, intensitySlider,
                    LanguageSettings.getTranslated("contrastIntensity"), JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, contrastIcon,
                    new String[] { LanguageSettings.getTranslated("ok"), LanguageSettings.getTranslated("cancel") },
                    null);

            // Check the return value from the dialog box.
            if (option == 1 || option == -1) {
                target.getImage().undo();
                target.getImage().popRedo();

                target.repaint();
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                int intensity = intensitySlider.getValue();
                try {
                    if(intensity != 0){
                        target.getImage().undo();
                        target.getImage().popRedo();

                        target.getImage().apply(new ContrastBrightnessAdjust(intensity, 0));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                } catch (NullPointerException exception) {
                    Object[] options = { LanguageSettings.getTranslated("ok") };
                    JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"),
                            LanguageSettings.getTranslated("alert"),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
                return;
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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BrightnessAdjustAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            // Set the hotkey 'ctrl + b' to trigger a contrastAdjustAction action.
            KeyStroke o = KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
            putValue(Action.ACCELERATOR_KEY, o);

            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(o, getValue(Action.NAME));

            target.getActionMap().put(getValue(Action.NAME), this);
        }

        /**
         * <p>
         * Callback for when the brightnessAdjustAction action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the brightnessAdjustAction is triggered.
         * It opens a JSlider window that allows the user to adjust the image's
         * brightness in between -100 and 100.
         * Once brightness has been selected the method will create an instance of
         * ContrastBrightnessAdjust passing the value to the brightness parameter
         * and 0 to the contrast parameter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                return;
            }
            // Pop-up dialog box to ask for the intensity value of the brightness.
            DefaultBoundedRangeModel intensityModel = new DefaultBoundedRangeModel(0, 0, -100, 100);
            JSlider intensitySlider = new JSlider(intensityModel);
            intensitySlider.setPreferredSize(new Dimension(300, 50));
            intensitySlider.setMajorTickSpacing(25);
            intensitySlider.setMinorTickSpacing(5);
            intensitySlider.setPaintTicks(true);
            intensitySlider.setSnapToTicks(enabled);

            intensitySlider.addChangeListener(new ChangeListener() {
                int editCounter = 0;

                public void stateChanged(ChangeEvent e) {
                    if (editCounter > 0) {
                        target.getImage().undo();
                        target.getImage().popRedo();
                    }
                    editCounter++;
                    int brightness = intensitySlider.getValue();

                    // Create and apply the filter.
                    try {
                        target.getImage().apply(new ContrastBrightnessAdjust(0, brightness));
                        target.repaint();
                        target.getParent().revalidate();
                    } catch (NullPointerException exception) {
                        Object[] options = { LanguageSettings.getTranslated("ok") };
                        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"),
                                LanguageSettings.getTranslated("alert"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    }
                }
            });

            intensitySlider.setPaintLabels(true);
            ImageIcon brightnessIcon = new ImageIcon("src/images/brightness-1.png", "Brightness Icon");
            int option = JOptionPane.showOptionDialog(null, intensitySlider,
                    LanguageSettings.getTranslated("brightnessIntensity"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, brightnessIcon,
                    new String[] { LanguageSettings.getTranslated("ok"), LanguageSettings.getTranslated("cancel") },
                    null);
            // Check the return value from the dialog box.
            if (option == 1 || option == -1) {
                target.getImage().undo();
                target.getImage().popRedo();

                target.repaint();
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                int intensity = intensitySlider.getValue();
                try {
                    if(intensity != 0){
                        target.getImage().undo();
                        target.getImage().popRedo();
                        
                        target.getImage().apply(new ContrastBrightnessAdjust(0, intensity));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                } catch (NullPointerException exception) {
                    Object[] options = { LanguageSettings.getTranslated("ok") };
                    JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"),
                            LanguageSettings.getTranslated("alert"),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
                return;
            }
        }

    }

}
