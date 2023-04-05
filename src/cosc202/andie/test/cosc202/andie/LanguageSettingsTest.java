package cosc202.andie.test.cosc202.andie;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import cosc202.andie.LanguageSettings;

public class LanguageSettingsTest {

    LanguageSettings newLS = new LanguageSettings();
 
    @Test
    void getTranslatedMaoriTest(){
        LanguageSettings.changeLang("Maori");
        String translated = LanguageSettings.getTranslated("open");
        assertEquals("Tuwhera", translated);
    }

    @Test
    void getTranslatedPirateTest(){
        LanguageSettings.changeLang("Pirate");
        String translated = LanguageSettings.getTranslated("exit");
        assertEquals("Pull Anchor", translated);
    }

    @Test
    void getTranslatedEnglishTest(){
        LanguageSettings.changeLang("English");
        String translated = LanguageSettings.getTranslated("exit");
        assertEquals("Exit", translated);
    }

    @Test
    void getTranslatedSpanishTest(){
        LanguageSettings.changeLang("Spanish");
        String translated = LanguageSettings.getTranslated("save");
        assertEquals("Ahorrar", translated);
    } 

}
