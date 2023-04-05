package cosc202.andie;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

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

    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(LanguageSettings.getTranslated("open"), null, LanguageSettings.getTranslated("openDesc"), Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(LanguageSettings.getTranslated("save"), null, LanguageSettings.getTranslated("saveDesc"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(LanguageSettings.getTranslated("saveAs"), null, LanguageSettings.getTranslated("saveAsDesc") , Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(LanguageSettings.getTranslated("export"), null, LanguageSettings.getTranslated("exportDesc"), Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileLanguage(LanguageSettings.getTranslated("language"), null, LanguageSettings.getTranslated("languageDesc") , Integer.valueOf(0)));
        actions.add(new FileExitAction(LanguageSettings.getTranslated("exit"), null, LanguageSettings.getTranslated("exitDesc"), Integer.valueOf(0)));
        
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
            int option = JOptionPane.showOptionDialog(null, langPanel, LanguageSettings.getTranslated("language"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
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
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
                int result = fileChooser.showOpenDialog(target);
                

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                       
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().open(imageFilepath);
                        //Checks the size of the image to make sure it is not larger than 4k.
                        if(target.getImage().getCurrentImage().getHeight() > 2160 || target.getImage().getCurrentImage().getWidth() > 3840){
                            flag = true;
                            UIManager.put("OptionPane.okButtonText", LanguageSettings.getTranslated("ok"));
                            JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("tooLarge"));
                        }else{
                            target.repaint();
                            target.getParent().revalidate();
                            flag = false;
                            break;
                        }
                        
                    } catch (Exception ex) {
                        UIManager.put("OptionPane.okButtonText", LanguageSettings.getTranslated("ok"));
                        JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("incompatible"));
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
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
                UIManager.put("OptionPane.okButtonText", LanguageSettings.getTranslated("ok"));
                JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("unsaved"));

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
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
                    UIManager.put("OptionPane.okButtonText", LanguageSettings.getTranslated("ok"));
                    JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("unsaved"));
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
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
                    UIManager.put("OptionPane.okButtonText", LanguageSettings.getTranslated("ok"));
                    JOptionPane.showMessageDialog(null, LanguageSettings.getTranslated("unsaved"));
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
