package com.exortions.premiumpunishments.util.lang;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.util.lang.languages.en_us;
import com.exortions.premiumpunishments.util.lang.languages.es_es;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@SuppressWarnings("ResultOfMethodCallIgnored")
public class LanguageManager {

    private String currentLanguage;
    private List<Language> languages;

    public LanguageManager(String currentLanguage) {
        this.currentLanguage = currentLanguage;

        languages = new ArrayList<>();

        languages.add(new en_us());
        languages.add(new es_es());

        File folder = new File(PremiumPunishments.getPlugin().getDataFolder() + File.separator + "lang");
        if (!folder.exists()) folder.mkdirs();

        loadLanguages();
    }

    public void loadLanguages() {
        for (Language language : languages) {
            language.write();
        }
    }

    public Language getCurrentLanguage() {
        for (Language language : languages) {
            if (language.getName().equals(currentLanguage)) return language;
        }
        return null;
    }

}
