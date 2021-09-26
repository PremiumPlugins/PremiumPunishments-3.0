package com.exortions.premiumpunishments.util;

import com.exortions.premiumpunishments.PremiumPunishments;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

public class LogManager {

    public static void addLog(Player player, String type, String reason, String target, String time) {
        String sql = "INSERT INTO `premiumpunishments`.`premiumpunishments_logs`" +
                "(`uuid`," +
                "`type`," +
                "`reason`," +
                "`target`," +
                "`username`," +
                "`date`," +
                "`time`)" +
                "VALUES(" +
                "uuid='" + player.getUniqueId() + "'," +
                "type='" + type + "'," +
                "reason='" + reason + "'," +
                "target='" + target + "'," +
                "username='" + player.getName() + "'," +
                "date='" + new Timestamp(System.currentTimeMillis()) + "'," +
                "time='" + time + "');";
        PremiumPunishments.getPlugin().getDatabase().execute(sql);
    }

}
