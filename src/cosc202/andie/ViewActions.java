package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 * 
 * <p>
 * The View menu contains actions that affect how the image is displayed in the application.
 * These actions do not affect the contents of the image itself, just the way it is displayed.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ViewActions {
    
    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();
    
        
        actions.add(new ZoomInAction(LanguageSettings.getTranslated("zoomIn"), null, "Zoom In", Integer.valueOf(KeyEvent.VK_PLUS)));
        actions.add(new ZoomOutAction(LanguageSettings.getTranslated("zoomOut"), null, "Zoom Out", Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new ZoomFullAction(LanguageSettings.getTranslated("zoomFull"), null, "Zoom Full", Integer.valueOf(KeyEvent.VK_1)));
        actions.add(new ImageRotationAction("Rotate Right", null, "Rotate the Image", Integer.valueOf(KeyEvent.VK_R), 2));
        actions.add(new ImageRotationAction("Rotate Right", null, "Rotate the Image", Integer.valueOf(KeyEvent.VK_R), 1));
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     */

    // public JMenu createMenu() {
    //     JMenu viewMenu = new JMenu(LanguageSettings.getTranslated("view"));

    public void createMenu(JMenuBar menu) {
        JButton rotateLeft = new JButton("<");
        JButton rotateRight = new JButton(">");
        JButton zoomIn = new JButton("+");
        JButton zoomOut = new JButton("-");
        JButton fitScreen = new JButton("[]");

        rotateLeft.addActionListener(actions.get(3));
        rotateRight.addActionListener(actions.get(4));
        zoomOut.addActionListener(actions.get(1));
        zoomIn.addActionListener(actions.get(0));
        fitScreen.addActionListener(actions.get(2));

        menu.add(rotateLeft);
        menu.add(rotateRight);
        menu.add(zoomIn);
        menu.add(zoomOut);
        menu.add(fitScreen);
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom()+10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-iout action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom()-10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(100);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    
    public class ImageRotationAction extends ImageAction {
        int rotation;
        /**
         * <p>
         * Create a new ImageRotation action.
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
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new ImageRotation(rotation));
                target.repaint();
                target.getParent().revalidate();
            }catch(NullPointerException exception){
                JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("noInput"));
            }
        }

    }
    
    
}
