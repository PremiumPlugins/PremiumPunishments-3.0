package com.exortions.premiumpunishments.util.lang.languages;

import com.exortions.premiumpunishments.util.lang.Language;

import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public class es_es extends Language {

    public es_es() {
        super(null);
        setName("en_es");

        HashMap<String, String> map = new HashMap<>();

        map.put("only-players", "%prefix%&c¡Solo los jugadores pueden ejecutar este comando!r");
        map.put("no-permission", "&c¡No tienes permiso para ejecutar este comando!");
        map.put("unknown-command", "%prefix%&c¡Comando no encontrado! Utilice la ayuda de /premiumpunishments para obtener más información.");
        map.put("unknown-player", "%prefix%&cNo se pudo encontrar la jugadora por el nombre de %s!");

        setMap(map);
    }

}
