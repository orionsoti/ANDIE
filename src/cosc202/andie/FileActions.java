package cosc202.andie;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications, 
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {
    
    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    /**
     * <p>
     * Creating a menu for file actions from array
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(LanguageSettings.getTranslated("open"), new ImageIcon("src/images/open_small.png"), LanguageSettings.getTranslated("openDesc"), Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(LanguageSettings.getTranslated("save"), new ImageIcon("src/images/save_small.png"), LanguageSettings.getTranslated("saveDesc"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(LanguageSettings.getTranslated("saveAs"), new ImageIcon("src/images/save-as_small.png"), LanguageSettings.getTranslated("saveAsDesc") , Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(LanguageSettings.getTranslated("export"), new ImageIcon("src/images/export_small.png"), LanguageSettings.getTranslated("exportDesc"), Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileLanguage(LanguageSettings.getTranslated("language"), new ImageIcon("src/images/language_small.png"), LanguageSettings.getTranslated("languageDesc") , Integer.valueOf(0)));
        actions.add(new FileExitAction(LanguageSettings.getTranslated("exit"), new ImageIcon("src/images/exit_small.png"), LanguageSettings.getTranslated("exitDesc"), Integer.valueOf(0)));
        
    }
    /**
     * <p>
     * Action to change the ANDIE applications language.
     * </p>
     */

    public class FileLanguage extends AbstractAction {

        FileLanguage(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            /**
             * <p>
             * Action to open popup for language selection with a combo box displaying all options.
             * </p>
             */
            
            /*JFrame f = new JFrame("frame");
            f.setSize(200,200);
            String s1[] = {"English", "Spanish","Maori", "Pirate"};
            JComboBox<String> c1 = new JComboBox<>(s1);
            JButton b12 = new JButton("ok");
            JPanel p = new JPanel();
            p.setSize(200,200);
            p.add(c1);
            p.add(b12);*/
        
            
            JPanel langPanel = new JPanel();
            langPanel.add(new JLabel(LanguageSettings.getTranslated("langSelect")));

            String[] langList = {"English", "Spanish", "Maori", "Pirate"};
            JComboBox<String> langBox = new JComboBox<>(langList);
            langPanel.add(langBox);
            int option = JOptionPane.showOptionDialog(null, langPanel, LanguageSettings.getTranslated("language"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);
            if (option == JOptionPane.OK_OPTION) {
                String selc = langBox.getSelectedItem().toString();
                LanguageSettings.changeLang(selc);
                Andie.createMenuBar();
            }else{
                return;
            }

            /** 
             * <p>
             * Action when the ok button is pressed it calls a method todo multiple things:
             * Calls the changelang method which updates prefrences and reloads appropriate bundle
             * recalls the createMenuBar method with the now updated language
             * brings the language box back to the front of the screen for user to change back or exit
             * </p>
             */
            /*b12.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String selc = c1.getSelectedItem().toString();
                    LanguageSettings.changeLang(selc);
                    Andie.createMenuBar();
                    f.toFront();
                }
            });
            f.add(p);
            f.setSize(400,300);
            f.setVisible(true);*/

        }
    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageSettings.getTranslated("file"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Creates and adds file action buttons to the toolBar.
     * </p>
     * 
     * @param toolBar Target JMenuBar that the buttons are added to.
     */
    public void createToolMenu(JMenuBar toolBar){
        //Creates the buttons
        JButton open = new JButton(new ImageIcon("src/images/open1.png"));
        JButton save = new JButton(new ImageIcon("src/images/save1.png"));

        //Adds the action listeners
        save.addActionListener(actions.get(1));
        open.addActionListener(actions.get(0));

        //Sets the size of the buttons
        save.setPreferredSize(Andie.buttonSize);
        open.setPreferredSize(Andie.buttonSize);

        //Sets the tool tips for the buttons
        save.setToolTipText(LanguageSettings.getTranslated("save"));
        open.setToolTipText(LanguageSettings.getTranslated("open"));

        //Removes the border and focus from the buttons
        open.setFocusPainted(false);
        save.setFocusPainted(false);
        open.setBorderPainted(false);
        save.setBorderPainted(false);
        
        //Adds the buttons to the tool bar
        toolBar.add(open);
        toolBar.add(save);

        // Disable the border
        toolBar.setBorder(BorderFactory.createEmptyBorder());
        
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * Sets the hotkey for file-open as 'ctrl + o'. 
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
           
            // Set the hotkey 'ctrl + o' to trigger a file-open action.
            KeyStroke o = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
            putValue(Action.ACCELERATOR_KEY, o);
            
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(o, getValue(Action.NAME));
    
            target.getActionMap().put(getValue(Action.NAME), this);
             
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * If this image is larger than our limit of 2160x3840 the applications will inform the user.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            boolean flag = false;
            
            do{
                JFileChooser fileChooser = new JFileChooser();
                // Sets the file chooser to only accept image files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Supported Picture Files", "jpg", "jpeg",
                    "png", "bmp", "gif", "tif", "tiff", "wbmp");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(target);
                

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                       
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().open(imageFilepath);
                        //Andie.menuBar1.pack(); // resize the window to fit the new image
                        //Checks the size of the image to make sure it is not larger than 4k.
                        if(target.getImage().getCurrentImage().getHeight() > 2160 || target.getImage().getCurrentImage().getWidth() > 3840){
                            flag = true;
                            Object[] options = {LanguageSettings.getTranslated("ok")};
                            JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("tooLarge"), LanguageSettings.getTranslated("alert"),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                        }else{
                            target.repaint();
                            target.getParent().revalidate();
                            flag = false;
                            break;
                        }
                        
                    } catch (Exception ex) {
                        Object[] options = {LanguageSettings.getTranslated("ok")};
                        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("incompatible"), LanguageSettings.getTranslated("alert"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                        
                    }
                }else if(result == JFileChooser.CANCEL_OPTION){ //File menu wouldn't close if an image that was too large was chosen, added this to counter-act
                    flag = false;
                    break;
                }
            }while(flag == true);
        }

    }


    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * Sets 'ctrl + s' (windows) and 'cmd + s' (mac) as the hotkey for file-save.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        
             // Sets the hotkey as 'ctrl + s' to trigger a file-save action.
             KeyStroke s = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
             putValue(Action.ACCELERATOR_KEY, s);
             
             InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
             inputMap.put(s, getValue(Action.NAME));
     
             target.getActionMap().put(getValue(Action.NAME), this);

        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().save();           
            } catch (Exception ex) {
                Object[] options = {LanguageSettings.getTranslated("ok")};
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("unsaved"), LanguageSettings.getTranslated("alert"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);

            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * Sets 'ctrl + shift + s' as the hotkey for save-as.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

             // Sets the hotkey to 'ctrl + shift + s' to trigger a Save-as action.
             KeyStroke sA = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | InputEvent.SHIFT_DOWN_MASK, enabled);
             putValue(Action.ACCELERATOR_KEY, sA);
             
             InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
             inputMap.put(sA, getValue(Action.NAME));
     
             target.getActionMap().put(getValue(Action.NAME), this);
        }

         /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);
                } catch (Exception ex) {
                    Object[] options = {LanguageSettings.getTranslated("ok")};
                    JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("unsaved"), LanguageSettings.getTranslated("alert"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                }
            }
        }

    }
    
    /**
     * Exports the Image to specified file
     * @see EditableImage#exportImage(String)
     */
    public class FileExportAction extends ImageAction {

        /**
         * <p>
         * Create a new file export action.
         * Sets the hotkey as 'ctrl + shift + e' for file-export.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

            // Sets the hotkey to 'ctrl + shift + e' to trigger a File-Export action.
            KeyStroke eX = KeyStroke.getKeyStroke(KeyEvent.VK_E,  Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | InputEvent.SHIFT_DOWN_MASK, enabled);
            putValue(Action.ACCELERATOR_KEY, eX);
            
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(eX, getValue(Action.NAME));
    
            target.getActionMap().put(getValue(Action.NAME), this);
            
        }

         /**
         * <p>
         * Callback for when the fileExport action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().exportImage(imageFilepath);
                } catch (Exception ex) {
                    Object[] options = {LanguageSettings.getTranslated("ok")};
                    JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("unsaved"), LanguageSettings.getTranslated("alert"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                }
            }
        }

    }
    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         *
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            
            
        }

         /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

}
