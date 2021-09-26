package com.exortions.premiumpunishments.util.lang.languages;

import com.exortions.premiumpunishments.util.lang.Language;

import java.util.HashMap;

public class en_us extends Language {

    public en_us() {
        super(null);
        setName("en_us");

        HashMap<String, String> map = new HashMap<>();

        map.put("only-players", "%prefix%&cOnly players can execute this command!");
        map.put("no-permission", "&cYou don't have permission to execute this command!");
        map.put("unknown-command", "%prefix%&cCommand not found! Use /premiumpunishments help for more info.");
        map.put("unknown-player", "%prefix%&cCould not find player by the name of %s!");

        setMap(map);
    }
}
