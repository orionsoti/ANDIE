package cosc202.andie;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Custom colour chooser for drawing function
 * 
 * @author Orion Soti
 * @version 1.0
 */
public class ColourChooser extends JPanel implements ChangeListener{
     /**
     * Initilizes a colour variable so it can be called from multiple methods. 
     */
    public static Color colour;
    private JColorChooser colourChooser;

    /** 
     * Overridden method from ChangeListener interface
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        colour = colourChooser.getColor();
    }

    /**
     * Constructor for ColourChooser class that creates a custom colour chooser using only the HSV colour panel
     * 
     */
    public ColourChooser(){
        colourChooser = new JColorChooser();
        Component[] components = colourChooser.getComponents();
        for (Component comp : components) {
            comp.setVisible(false);
        }
        AbstractColorChooserPanel[] panels = colourChooser.getChooserPanels();
        AbstractColorChooserPanel[] panel = new AbstractColorChooserPanel[1];
        panel[0] = panels[1];
        panel[0].getComponent(0).setVisible(false);
        colourChooser.setChooserPanels(panel);
        colourChooser.getSelectionModel().addChangeListener(this);
        JPanel colourChooserPanel = new JPanel();
        colourChooserPanel.add(colourChooser);
        this.add(colourChooserPanel);
        colour = colourChooser.getColor();
    }

    
}
