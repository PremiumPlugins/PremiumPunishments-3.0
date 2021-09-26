package com.exortions.premiumpunishments.util.lang;

import com.exortions.pluginutils.config.Configuration;
import com.exortions.premiumpunishments.PremiumPunishments;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Language {

    private String name;
    private Configuration config;
    private HashMap<String, String> map;

    public Language(HashMap<String, String> map) {
        name = getClass().getSimpleName();

        this.map = map;
    }

    public void write() {
        config = new Configuration(PremiumPunishments.getPlugin(), "lang" + File.separator + name + ".lang.yml");

        try {
            if (!config.getFile().exists()) config.createFile();

            for (Map.Entry<String, String> s : map.entrySet()) config.set(s.getKey(), s.getValue(), false);

            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String path) {
        return new Configuration(PremiumPunishments.getPlugin(), "lang" + File.separator + name + ".lang.yml").getString(path);
    }

}