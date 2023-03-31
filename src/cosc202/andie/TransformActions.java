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
 */
public class TransformActions {
    /**
    * A list of actions for the Transform menu.
    */
    protected ArrayList<Action> actions;

    public TransformActions(){
        actions = new ArrayList<Action>();
        actions.add(new FlipAction("Flip Vertical", null, "Flip image vertically", null, Flip.FLIP_VERTICAL));
        actions.add(new FlipAction("Flip Horizontal", null, "Flip image horizontally", null, Flip.FLIP_HORIZONTAL));
        actions.add(new ResizeAction("Resize", null, "Resize image", null));
        
    }
    /*
     * Create a menu containing the list of Transform actions.
     */
    public JMenu createMenu(){
        JMenu transformMenu = new JMenu("Transform");
        for (Action action: actions) {
            transformMenu.add(new JMenuItem(action));
        }
        return transformMenu;
    }

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

    public class ResizeAction extends ImageAction{
        public int height;
        public int width;
        public double scale;

        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            try{
                height = target.getImage().getCurrentImage().getHeight();
                width = target.getImage().getCurrentImage().getWidth();
                scale = 1.0;

                SpinnerNumberModel heightSpinner = new SpinnerNumberModel(height, 0, 10000, 1);
                SpinnerNumberModel widthSpinner = new SpinnerNumberModel(width, 0, 10000, 1);
                SpinnerNumberModel scaleSpinner = new SpinnerNumberModel(scale, 0.0, 100.0, 0.1);

                JSpinner h = new JSpinner(heightSpinner);
                JSpinner w = new JSpinner(widthSpinner);
                JSpinner s = new JSpinner(scaleSpinner);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Height:"));
                myPanel.add(h);
                myPanel.add(Box.createHorizontalStrut(15));
                myPanel.add(new JLabel("Width:"));
                myPanel.add(w);
                myPanel.add(Box.createHorizontalStrut(15));
                myPanel.add(new JLabel("Scale:"));
                myPanel.add(s);

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                        "Resize", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    updateValues((int) h.getValue(), (int) w.getValue(), (double) s.getValue());
                
                    target.getImage().apply(new Resize(height, width, scale));
                    target.repaint();
                    target.getParent().revalidate();
                    
                }
            } catch(NullPointerException exception){
                JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("noInput"));
            }
        }
        
        public void updateValues(int outputHeight, int outputWidth, double outputScale) {
            if (outputHeight != height) {
                scale = ((double) outputHeight) / ((double)height);
                width = (int) (scale * width);
                height = outputHeight;
            } else if (outputWidth != width) {
                scale = ((double) outputWidth) / ((double) width);
                height = (int) (scale * height);
                width = outputWidth;
            } else if (outputScale != scale) {
                scale = outputScale;
                width = (int) (scale * width);
                height = (int) (scale * height);
            }
        }
    }
}
