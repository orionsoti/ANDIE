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
            target.getImage().apply(new Flip(direction));
            target.repaint();
            target.getParent().revalidate();
        }
    }
}
