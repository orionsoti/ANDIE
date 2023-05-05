package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
//import java.awt.*;


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
    
    //Mine
    static Stack<ImageOperation> CurrentMacroStack;
    static Stack<ImageOperation> Preset1;
    static Stack<ImageOperation> Preset2;
    static Boolean preset1check = false;
    static Boolean preset2check = false;
    static String Preset1Name;
    static String Preset2Name;
    static String macroFolderPath;
    static Boolean recording = false;

    /**
     * <p>
     * Creates the menu bar, initializes variables and creates a path for the read and write features
     * </p>
     */
    public MacroActions() {
        CurrentMacroStack = new Stack<ImageOperation>();
        File file = new File("");
        String path = file.getAbsolutePath();
        macroFolderPath = path + "/src/cosc202/andie/macros/";
        ReloadPresets();
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
        actions.add(new MacroStart("Start Macro", null, LanguageSettings.getTranslated("contrastDesc"), Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new MacroEnd("End Macro", null, LanguageSettings.getTranslated("brightnessDesc"), Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new MacroLoad("Load From File", null, LanguageSettings.getTranslated("greyscaleDesc"), Integer.valueOf(KeyEvent.VK_G)));
        
        //checks to make sure there is a preset in the txt file before loading it to the actions menu
        if(preset1check == true){
            actions.add(new Macro1("Preset1 ("+Preset1Name+")", null, LanguageSettings.getTranslated("greyscaleDesc"), Integer.valueOf(KeyEvent.VK_G)));
        }
        if(preset2check == true){
            actions.add(new Macro2("Preset2 ("+Preset2Name+")", null, LanguageSettings.getTranslated("greyscaleDesc"), Integer.valueOf(KeyEvent.VK_G)));
        }
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

        //Adds action listeners
        m1.addActionListener(actions.get(3));
        m2.addActionListener(actions.get(4));

        //Sets the button size
        m1.setPreferredSize(Andie.buttonSize);
        m2.setPreferredSize(Andie.buttonSize);

        //m1.setToolTipText(LanguageSettings.getTranslated("Apply Macro One"));
        //m2.setToolTipText(LanguageSettings.getTranslated("Apply Macro Two"));

        //Adds to toolBar
        toolBar.add(m1);
        toolBar.add(m2);
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
     * Create a menu contianing the list of Colour actions.
     * </p>
     */
    public void rewriteFile(String presetName, int presetChosen){
        
        try{
            //creates a new scanner to make variables for what is already in the Presets txt file
            Scanner sc = new Scanner(new File(macroFolderPath + "Presets.txt"));
            String preset1 = sc.nextLine();
            String preset2 = sc.nextLine();
            String presetText = preset1 + "\n" + preset2;
            String newPresetText = "";
            StringBuffer sb = new StringBuffer();
            sb.append(preset1 + System.lineSeparator());
            sb.append(preset2);
            
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

            FileWriter fw = new FileWriter(macroFolderPath + "Presets.txt");

            fw.append(newPresetText);
            fw.flush();
            fw.close();
            sc.close();
        }catch(IOException ex){
            System.out.println("Yo shit is wack duuuude");
        }
        
    }









    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     */
    public void changePreset(String presetName, Stack<ImageOperation> loadedOps){
        System.out.println(presetName);
     String[] imageExtensions = {"Preset 1", "Preset 2", "cancel",}; // valid image extensions to be checked
     int presetChosen = JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("selectFormat"), LanguageSettings.getTranslated("format"), JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, imageExtensions, imageExtensions[0]); 
     //ADD SWITCH AND NEW OPEN ENDED METHOD TO ACEPT BOTH OPTIOONS

     try{
        Scanner sc = new Scanner(new File(macroFolderPath + "Presets.txt"));
        String preset1 = sc.nextLine();
        String preset2 = sc.nextLine();
        String presetText = preset1 + "\n" + preset2;
        String newPresetText = "";


        StringBuffer sb = new StringBuffer();
        sb.append(preset1 + System.lineSeparator());
        sb.append(preset2);
        
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

        FileWriter fw = new FileWriter(macroFolderPath + "Presets.txt");

        fw.append(newPresetText);
        fw.flush();
        fw.close();     
    }
    catch(IOException ex){
        ex.printStackTrace();
    }
        
        Andie.createMenuBar();
    }


    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageSettings.getTranslated("colour"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public static boolean isRecording(){
        return recording;
    }
    public static void addToStack(ImageOperation op){
        CurrentMacroStack.add(op);
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


            File file = new File("");
            String path = file.getAbsolutePath();



            JFileChooser fileChooser = new JFileChooser(path + "/src/cosc202/andie/macros");
            int result = fileChooser.showOpenDialog(target);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    System.out.println(imageFilepath);
                    int pathSize = imageFilepath.length();
                    int placeholder = imageFilepath.indexOf("macros");
                    String blah = imageFilepath.substring(placeholder + 7, pathSize);
                    
                    

                    try {
                        FileInputStream fileIn = new FileInputStream(imageFilepath);
                        ObjectInputStream objIn = new ObjectInputStream(fileIn);
            
                        @SuppressWarnings("unchecked")
                        Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
                        
                        //boolean extensionCheck = false;
                        String[] imageExtensions = {"apply", "preset", "cancel",}; // valid image extensions to be checked
                        

                            /// if the extension is invalid an option dialog will prompt the user to select a valid extension
                        int applyOrPreset = JOptionPane.showOptionDialog(null, "Please choose an action to preform on loaded file", "File Action", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, imageExtensions, imageExtensions[0]); 
                        
                        switch(applyOrPreset){
                            case 0:
                                target.getImage().MacroAddition(opsFromFile);//hereman
                                target.repaint();
                                target.getParent().revalidate();
                                break;
                            case 1:
                            changePreset(blah,opsFromFile);
                            ReloadPresets();
                                break;
                            case 2: 
                                break;
                        } 

                        objIn.close();
                        fileIn.close();

                    } catch (Exception ex) {
                        // Could be no file or something else. Carry on for now.
                    }
                
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
         * Callback for when the ContrastAdjustAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ContrastAdjustAction is triggered.
         * It opens a JSlider window that allows the user to adjust the image's contrast in between -100 and 100.
         * Once contrast has been selected will create an instance of ContrastBrightnessAdjust passing the value to the contrast parameter
         * and 0 to the brightness parameter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            recording = true;            
        }

    }
     /**
     * <p>
     * Action to end the .
     * </p>
     * 
     * @see ContrastBrightnessAdjust
     */

    public class MacroEnd extends ImageAction {

        /**
         * <p>
         * Create a new brightness adjustment action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MacroEnd(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        /**
         * <p>
         * Callback for when the brightnessAdjustAction action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the brightnessAdjustAction is triggered.
         * It opens a JSlider window that allows the user to adjust the image's brightness in between -100 and 100.
         * Once brightness has been selected the method will create an instance of ContrastBrightnessAdjust passing the value to the brightness parameter
         * and 0 to the contrast parameter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            recording = false;

            JPanel userAskPanel = new JPanel();
            //userAskPanel.add(new JLabel(LanguageSettings.getTranslated("langSelect")));
            String userValue = JOptionPane.showInputDialog(userAskPanel, "Please choose a name for your macro");

            //ASKS USER FOR PRESET SLOT

                System.out.println(userValue);//popUpSelection);

                File file = new File("");
                String path = file.getAbsolutePath();
                System.out.println(path);
                String pathWithExtension = path + "/src/cosc202/andie/macros/" + userValue + ".ops";

                //TAKES STACK AND SAVES IT MIGHT NEED A ER
                try{
                FileOutputStream fileOut = new FileOutputStream(pathWithExtension);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

                objOut.writeObject(CurrentMacroStack);
                objOut.close();
                fileOut.close();

                } catch (Exception ex) {
                // Could be no file or something else. Carry on for now.
                System.out.println("FAILED TO SAVE STACK AS OPS FILE");
                }

                CurrentMacroStack = new Stack<ImageOperation>();

            //}else{
            //   return;
            //}                       
        }

    }

    public class Macro1 extends ImageAction {

        /**
         * <p>
         * Create a new contrast adjustment action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Macro1(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        /**
         * <p>
         * Callback for when the ContrastAdjustAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ContrastAdjustAction is triggered.
         * It opens a JSlider window that allows the user to adjust the image's contrast in between -100 and 100.
         * Once contrast has been selected will create an instance of ContrastBrightnessAdjust passing the value to the contrast parameter
         * and 0 to the brightness parameter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().MacroAddition(Preset1);//hereman
            target.repaint();
            target.getParent().revalidate();          
        }

    }

    public class Macro2 extends ImageAction {

        /**
         * <p>
         * Create a new contrast adjustment action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Macro2(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        /**
         * <p>
         * Callback for when the ContrastAdjustAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ContrastAdjustAction is triggered.
         * It opens a JSlider window that allows the user to adjust the image's contrast in between -100 and 100.
         * Once contrast has been selected will create an instance of ContrastBrightnessAdjust passing the value to the contrast parameter
         * and 0 to the brightness parameter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().MacroAddition(Preset2);//hereman
            target.repaint();
            target.getParent().revalidate();          
        }

    }

}


