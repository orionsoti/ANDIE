package cosc202.andie;

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.Dimension;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {
    
     /**
     * Sets default size of buttons (used to tidy up look of application making icons fit nicely within buttons).
     */
    public static Dimension buttonSize = new Dimension(35,35);
     /**
     * Initializes the menu bar so it can be called from locations outside the createMenuBar method. 
     */
    public static JFrame menuBar1;
    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an {@code ImagePanel})
     * Calls upon createMenuBar to complete the set up of the program
     * </p>
     * 
     * @throws Exception if something goes wrong.
     */

    

    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        menuBar1 = frame;
        // customise the frame a bit
        frame.setPreferredSize(new Dimension(800,800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Add in menus for various types of action the user may perform.
        createMenuBar();
        createToolMenu();
    }
    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets various menus which can be used to trigger operations to load, save, edit, etc. 
     * These operations are implemented {@link ImageOperation}s and triggerd via 
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * <P>
     * This has been seperated from the createAndShowGUI() method for the purpose of recalling when the language changes.
     * See LanguageSettings class for more details.
     * </P>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * 
     */
    public static void createMenuBar(){
        //menuBar1.setVisible(false);
        JMenuBar menuBar = new JMenuBar();
        // File menus are pretty standard, so things that usually go in File menus go here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());
        
        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());
        
        // View actions control how the image is displayed, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());
        
        // Filters apply a per-pixel operation to the image, generally based on a local window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());
        
        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());
        
        TransformActions transformActions = new TransformActions();
        menuBar.add(transformActions.createMenu());

        MacroActions macroActions = new MacroActions();
        menuBar.add(macroActions.createMenu());
        
        // View actions control how the image is displayed, but do not alter its actual content
        // Rather than making the menu and adding it to menuBar it directly adds the buttons to
        // menuBar
        //create menu to the right of the menu bar

        // ViewActions viewActions2 = new ViewActions();
        // menuBar.add(viewActions2.createToolMenu());

        menuBar1.setJMenuBar(menuBar);
        //menuBar1.pack();
        menuBar1.revalidate();
        menuBar1.repaint();
        menuBar1.setVisible(true);
    }

    /**
     * <p>
     * Displays a popup to inform user that there is no image currently loaed in.
     * </p>
     * <p>
     * Used to stop actions being applied on nothing. Put in the main andie file as it is
     * used multiple times thorughout the application and this streamlines other files.
     * </p>
     */   
    public static void noImageErrorMsg(){
        Object[] options = { LanguageSettings.getTranslated("ok") };
        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noInput"),
                LanguageSettings.getTranslated("alert"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    }
    /**
     * <p>
     * Creates the toolbar menu.
     * </p>
     * <p>
     * Calls toolMenu methods from each catagory to add all required shortcuts to the toolbar.
     * </p>
     */
    public static void createToolMenu(){
        //menuBar1.setVisible(false);
        JMenuBar toolBar = new JMenuBar();

        FileActions fileActions = new FileActions();
        ViewActions viewActions = new ViewActions();
        EditActions editActions = new EditActions();
        TransformActions transformActions = new TransformActions();
        MacroActions macroActions = new MacroActions();

        fileActions.createToolMenu(toolBar);
        editActions.createToolMenu(toolBar);
        viewActions.createToolMenu(toolBar);
        transformActions.createToolMenu(toolBar);
        macroActions.createToolMenu(toolBar);

        menuBar1.add(toolBar, BorderLayout.NORTH);
        menuBar1.revalidate();
        menuBar1.repaint();
        menuBar1.setVisible(true);
        //menuBar1.pack();
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {

        LanguageSettings settings = new LanguageSettings();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
