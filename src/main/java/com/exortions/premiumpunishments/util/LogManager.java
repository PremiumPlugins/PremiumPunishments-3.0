package com.exortions.premiumpunishments.util;

import com.exortions.premiumpunishments.PremiumPunishments;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

public class LogManager {

    public static void addLog(Player player, String type, String reason, String target, String time) {
        String sql = "INSERT INTO `premiumpunishments`.`" + PremiumPunishments.tablePrefix + "logs`" +
                "(`uuid`," +
                "`type`," +
                "`reason`," +
                "`target`," +
                "`username`," +
                "`date`," +
                "`time`)" +
                "VALUES(" +
                "'" + player.getUniqueId() + "'," +
                "'" + type + "'," +
                "'" + reason + "'," +
                "'" + target + "'," +
                "'" + player.getName() + "'," +
                "'" + new Timestamp(System.currentTimeMillis()) + "'," +
                "'" + time + "');";
        PremiumPunishments.getPlugin().getDatabase().execute(sql);
    }

}
