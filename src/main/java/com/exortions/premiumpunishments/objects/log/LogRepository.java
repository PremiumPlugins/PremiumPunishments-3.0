package com.exortions.premiumpunishments.objects.log;

import com.exortions.premiumpunishments.PremiumPunishments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogRepository {

    public static List<Log> getLogsByUuid(String uuid) {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "logs WHERE uuid='" + uuid + "'");

            List<Log> logs = new ArrayList<>();

            while (set.next()) {
                logs.add(new Log(set.getString("uuid"), set.getString("type"), set.getString("reason"), set.getString("target"), set.getString("username"), set.getTimestamp("date"), set.getString("time")));
            }

            return logs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
