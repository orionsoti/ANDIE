package cosc202.andie;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Transform
 * <p>
 * 
 * <p>
 * The Transform menu contains actions such as resize, rotate, and flip that affect the contents of the image.
 * <p>
 * 
 * @author Orion Soti & Jacob Myron
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
        actions.add(new ResizeAction(LanguageSettings.getTranslated("resize"), null, "Resize image", null));
        actions.add(new ImageRotationAction(LanguageSettings.getTranslated("rotateLeft"), null, "Rotate the Image", Integer.valueOf(KeyEvent.VK_R), 2));
        actions.add(new ImageRotationAction(LanguageSettings.getTranslated("rotateRight"), null, "Rotate the Image", Integer.valueOf(KeyEvent.VK_R), 1));
        actions.add(new FlipAction(LanguageSettings.getTranslated("flipVertical"), null, "Flip image vertically", null, Flip.FLIP_VERTICAL));
        actions.add(new FlipAction(LanguageSettings.getTranslated("flipHorizontal"), null, "Flip image horizontally", null, Flip.FLIP_HORIZONTAL));
        
        
    }
    /*
     * Create a menu containing the list of Transform actions.
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
     * Action to flip an image.
     * </p>
     * 
     * @throws NullPointerException If there is no image loaded
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

        public void actionPerformed(ActionEvent e){
            // Create and apply the filter
            try{
                target.getImage().apply(new Flip(direction));
                target.repaint();
                target.getParent().revalidate();
            }catch(NullPointerException exception){
                JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("noInput"));
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
            try{
                height = target.getImage().getCurrentImage().getHeight();
                width = target.getImage().getCurrentImage().getWidth();
                scale = 1.0;

                // Create a dialog to get the scale factor from the user
                SpinnerNumberModel scaleSpinner = new SpinnerNumberModel(scale * 100, 0.0, 1000.0, 1);
                JSpinner s = new JSpinner(scaleSpinner);
                JPanel myPanel = new JPanel();
                ImageIcon resizeIcon = new ImageIcon("src/resize.png");
                myPanel.add(new JLabel("Scale (%)"));
                myPanel.add(s);
                int option = JOptionPane.showOptionDialog(target.getParent(), myPanel, "Resize", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, resizeIcon, null, null);
                if (option == JOptionPane.OK_OPTION) {
                    updateScale(scaleSpinner.getNumber().doubleValue()/100, width, height);
                }else if (option == JOptionPane.CANCEL_OPTION){
                    return;
                }

                target.getImage().apply(new Resize(height, width, scale));
                target.repaint();
                target.getParent().revalidate();
                
            } catch(NullPointerException exception){
                JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("noInput"));
            }
        }
        
        /**
         * Updates the values of the height, width, and scale based on the user input.
         * 
         * @param outputScale The scale of the output image.
         */
        public void updateScale(double outputScale, int outputWidth, int outputHeight) {
            if (outputScale != scale) {
                scale = outputScale;
                width = (int) (scale * width);
                height = (int) (scale * height);
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
         * @throws NullPointerException If there is no image loaded.
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