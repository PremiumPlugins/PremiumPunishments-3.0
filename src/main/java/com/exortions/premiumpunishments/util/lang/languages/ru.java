package com.exortions.premiumpunishments.util.lang.languages;

import com.exortions.premiumpunishments.util.lang.Language;

import java.util.HashMap;

public class ru extends Language {

    public ru() {
        super(null);
        setName("ru");

        HashMap<String, String> map = new HashMap<>();

        map.put("only-players", "%prefix%&cТолько игроки могут выполнять эту команду!");
        map.put("no-permission", "&cУ вас нет разрешения на выполнение этой команды!");
        map.put("unknown-command", "%prefix%&cКоманда не найдена! Для получения дополнительной информации используйте /premiumpunishments help.");
        map.put("unknown-player", "%prefix%&cНе удалось найти игрока с именем %s!");

        setMap(map);
    }

}
