package cosc202.andie;
import java.util.prefs.Preferences;
import java.util.*;



/**
 * <p>
 * Methods to alter language settings and get translated words
 * </p>
 * 
 * <p>
 * This class sets the prefrences and bundles for the application
 * includes methods for changing language mid run and for getting translated words
 * </p>
 * 
 * @author Tyler Birkett
 * @version 1.0
 */

public class LanguageSettings{

    private static Preferences prefs;
    private static ResourceBundle langBundle;

    /**
     * <p>
     * Constructor initiated before any Jpanels have been created in main
     * Sets the Prefrences and Resource Bundle 
     */

    public LanguageSettings(){  
        prefs = Preferences.userNodeForPackage(Andie.class);
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        langBundle = ResourceBundle.getBundle("cosc202.andie.LanguageBundle");              
    }
    
    /**
     * <p>
     * Method that is called when the language Combobox for FileActions.java is called:
     * When called will check which language has been selected then update prefrences and reload the new langBundle Resourse Bundle
     * </p>
     * 
     * @param s The language being switched to.
     */
    public static void changeLang(String s){
           
        if(s == "English"){
            prefs.put("language", "en");
            prefs.put("country", "NZ");
            Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
            langBundle = ResourceBundle.getBundle("cosc202.andie.LanguageBundle"); 
        }else if(s == "Spanish"){

            prefs.put("language", "sp");
            prefs.put("country", "SP");
            Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
            langBundle = ResourceBundle.getBundle("cosc202.andie.LanguageBundle"); 

        }else if(s == "Maori"){

            prefs.put("language", "mi");
            prefs.put("country", "NZ");
            Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
            langBundle = ResourceBundle.getBundle("cosc202.andie.LanguageBundle"); 
           

        }else if(s == "Pirate"){

            prefs.put("language", "pi");
            prefs.put("country", "NZ");
            Locale.setDefault(new Locale(prefs.get("language", "pi"), prefs.get("country", "NZ")));
            langBundle = ResourceBundle.getBundle("cosc202.andie.LanguageBundle"); 

        }else{
            prefs.put("language", "en");
            prefs.put("country", "NZ");
            Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
            langBundle = ResourceBundle.getBundle("cosc202.andie.LanguageBundle"); 
        }       

    }
    
    /**
     * <p>
     * method takes in a string input and returns a string from the current langBundle loaded
     * </p>
     * 
     * @param s the language to be translated to.
     * @return The translated statement.
     */
    public static String getTranslated(String s){
        return langBundle.getString(s);
    }
}