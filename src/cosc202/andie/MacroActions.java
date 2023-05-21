package cosc202.andie;

import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.Toolkit;


/**
 * <p>
 * Actions provided by the Macro menu
 * </p>
 * 
 * <p>
 * The Macro menu allows the user to record a macro of image 
 * operations then gives options to name, save as and apply to one of two preset buttons
 * 
 * </p>
 *
 * @author Tyler Birkett
 */
public class MacroActions {
    
     /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;
    
     //Holder for current macro recordings gets wiped when user ends recording
    static Stack<ImageOperation> CurrentMacroStack;
     //Stacks that hold the loaded in presets for when the user calls for the macro
    static Stack<ImageOperation> Preset1;
    static Stack<ImageOperation> Preset2;
     //flag to make sure there is a preset loaded in before adding it to the menu
    static Boolean preset1check = false;
    static Boolean preset2check = false;
     //Added here as many methods need these names and path and only requires the scanner to be called once
    static String Preset1Name;
    static String Preset2Name;
    static String macroFolderPath;
     //Used for many methods to not preform actions when recording
    static Boolean recording = false;

    

     /**
     * <p>
     * Creates the menu bar, initializes variables and creates a path for the read and write features
     * </p>
     */
    public MacroActions() {
         //initilizes the stack used for recording
        //ADSSDSDASDDDDDDDDDDDDDDDDDDDDDDDDDCurrentMacroStack = new Stack<ImageOperation>();
         //creats the file path for the Macros folder
        File file = new File("");
        String path = file.getAbsolutePath();
        macroFolderPath = path + "/src/cosc202/andie/macros/";
         //Creates the menu
        ReloadPresets();
    }



     /**
     * <p>
     * Loads the actions menu with all the drop down options. Has been relocated from MacroActions 
     * to allow for reloading when a macro preset has been changed. 
     * </p>
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageSettings.getTranslated("macros"));
        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }
        return fileMenu;
    }



     /**
     * <p>
     * Loads the actions menu with all the drop down options. Has been relocated from MacroActions 
     * to allow for reloading when a macro preset has been changed. 
     * </p>
     */
    public void ReloadPresets(){       
        actions = new ArrayList<Action>();      
         //calls the loadPresets method to load in any presets and flag the presets as true to allow menus to be created for them
        loadPresets();
         //creates instances of each macro menu option
        actions.add(new MacroStart(LanguageSettings.getTranslated("startMacro"), new ImageIcon("src/images/record_small.png"), LanguageSettings.getTranslated("startMDesc"), Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new MacroLoad(LanguageSettings.getTranslated("loadFromFile"), null, LanguageSettings.getTranslated("loadMDesc"), Integer.valueOf(KeyEvent.VK_G)));        
         //checks to make sure there is a preset in the txt file before loading it to the actions menu
        if(preset1check == true){actions.add(new Macro1(LanguageSettings.getTranslated("preset1") + " ("+Preset1Name + ")", null, LanguageSettings.getTranslated("presetDesc"), Integer.valueOf(KeyEvent.VK_G)));}
        if(preset2check == true){actions.add(new Macro2(LanguageSettings.getTranslated("preset2") + " ("+Preset2Name + ")", null, LanguageSettings.getTranslated("presetDesc"), Integer.valueOf(KeyEvent.VK_G)));}
    }

     /**
     * <p>
     * Called by any action in Andie that shouldnt be running when recording is in progress
     * Moved to here to clean up and compact code in other places
     * </p>
     */
    
    public static void macroRunningMsg(){
        Object[] options = {LanguageSettings.getTranslated("ok")};
        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("recordingInProgressMsg"), LanguageSettings.getTranslated("recordingInProgress"),
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
    }
    




     /**
     * <p>
     * Creates and adds edit action buttons to the toolBar.
     * </p>
     * 
     * @param toolBar Target JMenuBar that the buttons are added to.
     */
    public void createToolMenu(JMenuBar toolBar){
         //Creates the buttons
        JButton m1 = new JButton(new ImageIcon("src/images/macroOne.png"));
        JButton m2 = new JButton(new ImageIcon("src/images/macroTwo.png"));
        JButton m3 = new JButton(new ImageIcon("src/images/macroTwo.png"));

        m3.setIcon(new ImageIcon("src/images/record.png"));
        m3.addActionListener(actions.get(0));

         //Adds action listeners
        m1.addActionListener(actions.get(2));
        m2.addActionListener(actions.get(3));
         //Sets the button size and tooltips
        m1.setPreferredSize(Andie.buttonSize);
        m2.setPreferredSize(Andie.buttonSize);
        m3.setPreferredSize(Andie.buttonSize);
        //m1.setToolTipText(LanguageSettings.getTranslated("Apply Macro One"));
        //m2.setToolTipText(LanguageSettings.getTranslated("Apply Macro Two"));

        m1.setFocusPainted(false);
        m2.setFocusPainted(false);
        

        // Create a separator
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        Dimension separatorDimension = new Dimension(separator.getPreferredSize().width, toolBar.getPreferredSize().height);
        separator.setMaximumSize(separatorDimension);

        // Add the separator to the toolBar
        toolBar.add(separator);

         //Adds to toolBar
        toolBar.add(m1);
        toolBar.add(m2);
        toolBar.add(m3);
    }


    
     /**
     * <p>
     * Used to check if a recording is currently running to stop actions that can effect the recording.
     * Used in EditableImage.apply() to check if an operation should be added to a the macro stack 
     * </p>
     */   
    public static boolean isRecording(){
        return recording;
    }



     /**
     * <p>
     * Used for EditableImage.apply() along side MacroActions.isRecording() allows the image operation being applied to be passed over 
     * to Macroactions and saved to the macro stack
     * </p>
     */
    public static void addToStack(ImageOperation op){
        CurrentMacroStack.add(op);
    }


     /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     */
    public void changePreset(String presetName, Stack<ImageOperation> loadedOps){
        
        
        System.out.println(presetName);
        String[] imageExtensions = {LanguageSettings.getTranslated("preset1"), LanguageSettings.getTranslated("preset2"), LanguageSettings.getTranslated("cancel"),}; // valid image extensions to be checked
        int presetChosen = JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("selectPreset"), LanguageSettings.getTranslated("presets"), 
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, imageExtensions, imageExtensions[0]); 

        try{
            //creates a new scanner to make variables for what is already in the Presets txt file
        Scanner sc = new Scanner(new File(macroFolderPath + "Presets.txt"));
        String preset1 = sc.nextLine();
        System.out.println(preset1);
        String preset2 = sc.nextLine();
        System.out.println(preset2);
        String presetText = preset1 + "\n" + preset2;
        String newPresetText = "";
        StringBuffer sb = new StringBuffer();
        sb.append(preset1 + System.lineSeparator());
        sb.append(preset2);
            //takes the chosen preset and makes a string to replace the old file with the new preset included
        switch(presetChosen){
            case 0:
                newPresetText = presetText.replace(preset1,"Preset1="+ presetName);
                break;
            case 1:
                newPresetText = presetText.replace(preset2,"Preset2="+ presetName);
                break;
            case 2:
                System.out.println("See yaaaaaaaa"); 
                break;
        }
            //takes the newly created string and re writes the txt file 
        FileWriter fw = new FileWriter(macroFolderPath + "Presets.txt");
        fw.append(newPresetText);
        fw.flush();
        fw.close();
        sc.close();
            //catch incase string is invalid
        }catch(IOException ex){
        System.out.println("Yo shit is wack duuuude");
        }
         //reloads the menu bar with the new preset in place    
        Andie.createMenuBar();
    }



     /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     */
    public void loadPresets(){
        String macroPresetFileLocation = macroFolderPath + "Presets.txt";
         //attepts to read the Presets text file and if presets are avalible will load them into the class
        try{    
            File macroTxtFile = new File(macroPresetFileLocation);
            Scanner sc = new Scanner(macroTxtFile);
             //isolates the first preset name to allow for menu naming and loading of file
            String mac1 = sc.nextLine();
            int mac1Leng = mac1.length();
            String subMac =  mac1.substring(8, mac1Leng-4);
            Preset1Name = subMac;
            mac1 = macroFolderPath + subMac + ".ops" ;
             //checks to make sure there is something in the file then procedes to load in the first preset
            if(subMac.length() > 0){
                preset1check = true;
                try {
                    FileInputStream fileIn = new FileInputStream(mac1);
                    ObjectInputStream objIn = new ObjectInputStream(fileIn);
                    @SuppressWarnings("unchecked")
                    Stack<ImageOperation> opsForPreset1 = (Stack<ImageOperation>) objIn.readObject();
                    Preset1 = opsForPreset1;
                    objIn.close();
                    fileIn.close();
                } catch (Exception ex) {
                    // Could be no file or something else. Carry on for now.
                }
            }
             //isolates the second preset name to allow for menu naming and loading of file
            String mac2 = sc.nextLine();
            int mac2Leng = mac2.length();
            String subMac2 =  mac2.substring(8, mac2Leng-4);
            Preset2Name = subMac2;
            mac2 = macroFolderPath + subMac2 + ".ops" ;
             //checks to make sure there is something in the file then procedes to load in the second preset.
            if(subMac2.length() > 0){
                preset2check = true;
                try {
                    FileInputStream fileIn = new FileInputStream(mac2);
                    ObjectInputStream objIn = new ObjectInputStream(fileIn);
                    @SuppressWarnings("unchecked")
                    Stack<ImageOperation> opsForPreset2 = (Stack<ImageOperation>) objIn.readObject();
                    Preset2 = opsForPreset2;
                    objIn.close();
                    fileIn.close();
                } catch (Exception ex) {
                    // Could be no file or something else. Carry on for now.
                }
            }
            sc.close();
         //Displays an error message if the preset fails to load, will only happen when a user edits the preset.txt file cannot happen while using the ANDIE interface.
        }catch(FileNotFoundException e){
            System.out.println("Grabbing macros from txt file failed");
            e.printStackTrace();
        }
    }



    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     */
    public class MacroLoad extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MacroLoad(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
             //prompts the user to chose a file to load in as a macro
            JFileChooser fileChooser = new JFileChooser(macroFolderPath);
            int result = fileChooser.showOpenDialog(target);
            if (result == JFileChooser.APPROVE_OPTION) {
                 //isolates the file name to pass to the changePresetMethod
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    int pathSize = imageFilepath.length();
                    int placeholder = imageFilepath.indexOf("macros");
                    String isolatedFileName = imageFilepath.substring(placeholder + 7, pathSize);
                     //attempts to load in the selected ops file
                    try {
                        FileInputStream fileIn = new FileInputStream(imageFilepath);
                        ObjectInputStream objIn = new ObjectInputStream(fileIn);
                        @SuppressWarnings("unchecked")
                        Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
                         //asks the user to choose if they would like to apply or set as preset
                        String[] imageExtensions = {LanguageSettings.getTranslated("apply"), LanguageSettings.getTranslated("presets"), LanguageSettings.getTranslated("cancel"),}; // valid image extensions to be checked
                        int applyOrPreset = JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("selectLoadAction"), LanguageSettings.getTranslated("loadFromFile"), JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, imageExtensions, imageExtensions[0]); 
                         //switch takes users option and apply the appropriate methods to complete this
                        switch(applyOrPreset){
                            case 0:
                                target.getImage().MacroAddition(opsFromFile);//hereman
                                target.repaint();
                                target.getParent().revalidate();
                                break;
                            case 1:
                            changePreset(isolatedFileName,opsFromFile);
                            ReloadPresets();
                                break;
                            case 2: 
                                break;
                        } 
                        objIn.close();
                        fileIn.close();
                     //Displays an error if the loading of the ops file fails
                    } catch (Exception ex) {
                        // Could be no file or something else. Carry on for now.
                    }
                 //should be no reason for this to try to fail unless files are missing from Andie              
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        }
    }



     /**
     * <p>
     * Action to start the recording of a macro .
     * </p>
     */
    public class MacroStart extends ImageAction {
        /**
         * <p>
         * Create a new macro action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MacroStart(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);


        }        
        /**
         * <p>
         * Callback for when the MacroStart action is triggered.
         * Simply changes the recording boolean to allow any EditableImage.apply() calls to be recorded in the macro Stack
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            Andie.createToolMenu();
            
            if(target.getImage().hasImage()){


                if(isRecording() == false){
                    CurrentMacroStack = new Stack<ImageOperation>();
                    //macroStartMsg
                    
                    int option = JOptionPane.showOptionDialog(target.getParent(), LanguageSettings.getTranslated("macroStartMsg"), LanguageSettings.getTranslated("marcoStartTitle"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),new String[]{LanguageSettings.getTranslated("ok"),LanguageSettings.getTranslated("cancel")}, null);
                    if (option != JOptionPane.OK_OPTION) {
                        return;
                    }
                    recording = true;
                }else{
                 //turns off recording as the macro has been ended by user
                recording = false;
    
                //asks user for the name of the recorded macro and creates a file path to save to
               JPanel userAskPanel = new JPanel();
               String userValue = JOptionPane.showInputDialog(userAskPanel, LanguageSettings.getTranslated("nameMacro"));
               String pathWithExtension = macroFolderPath + userValue + ".ops";
    
                //takes the created stack and attepts to save it
               try{
                   FileOutputStream fileOut = new FileOutputStream(pathWithExtension);
                   ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                   objOut.writeObject(CurrentMacroStack);
                   objOut.close();
                   fileOut.close();
                } catch (Exception ex) {
                     //Could be no file or something else. Carry on for now.
                    System.out.println("FAILED TO SAVE STACK AS OPS FILE");
                }
                 //makes a new instance of the current marco stack so the next start of macro recording does not continue from the end of this one
                CurrentMacroStack = new Stack<ImageOperation>();   
    
                }
            }else{
                Andie.noImageErrorMsg();
            }
        }
    }




     /**
     * <p>
     * Action to end the Macro and prompt the user to save that macro.
     * </p>
     * 
     */

    public class Macro1 extends ImageAction {

         /**
         * <p>
         * Create a new action for Preset1.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Macro1(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
                        // Sets 'ctrl + f1' as the hotkey triggering  macro1 
                        KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
                        putValue(Action.ACCELERATOR_KEY, f1);
                        
                        InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                        inputMap.put(f1, getValue(Action.NAME));
                
                        target.getActionMap().put(getValue(Action.NAME), this);
            
        }

        /**
         * <p>
         * Callback for when the Macro1 is triggered.
         * </p>
         * 
         * <p>
         * This method applys the macro currently loacted in the preset1 loaction to the image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            if(!target.getImage().hasImage()){
                Andie.noImageErrorMsg();
            }else{
                try{ 
                    target.getImage().MacroAddition(Preset1);
                    target.repaint();
                    target.getParent().revalidate();          
                    }catch (Exception ex){
                        Object[] options = {LanguageSettings.getTranslated("ok") };
                        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("presetErrorMsg"),
                                LanguageSettings.getTranslated("missingPreset"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    }     
            }         
        }
    }

    public class Macro2 extends ImageAction {

        /**
         * <p>
         * Create a new action for Preset2.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Macro2(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
             // Sets 'ctrl + f2' as the hotkey triggering  macro2
             KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
             putValue(Action.ACCELERATOR_KEY, f2);
             
             InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
             inputMap.put(f2, getValue(Action.NAME));
     
             target.getActionMap().put(getValue(Action.NAME), this);
 
        }
        
        /**
         * <p>
         * Callback for when Macro2 is triggered.
         * </p>
         * 
         * <p>
         * This method applys the macro currently loacted in the preset2 loaction to the image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            if(!target.getImage().hasImage()){
                Andie.noImageErrorMsg();
            }else{
                try{ 
                    target.getImage().MacroAddition(Preset2);
                    target.repaint();
                    target.getParent().revalidate();          
                    }catch (Exception ex){
                        Object[] options = {LanguageSettings.getTranslated("ok") };
                        JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("presetErrorMsg"),
                                LanguageSettings.getTranslated("missingPreset"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    }     
            } 
        }

    }

}


