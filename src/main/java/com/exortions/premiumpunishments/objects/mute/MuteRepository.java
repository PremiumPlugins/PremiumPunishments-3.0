package com.exortions.premiumpunishments.objects.mute;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.MuteType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class MuteRepository {

    public static Mute getMuteByUuid(UUID uuid) {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "mutes WHERE uuid='" + uuid.toString() + "'");

            if (set.next()) {
                String username = set.getString("username");
                MuteType type = MuteType.valueOf(set.getString("mute-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                return new Mute(uuid.toString(), username, type, reason, admin, id);
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public static Mute getMuteByUuid(String uuid) {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "mutes WHERE uuid='" + uuid + "'");

            if (set.next()) {
                String username = set.getString("username");
                MuteType type = MuteType.valueOf(set.getString("mute-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                return new Mute(uuid, username, type, reason, admin, id);
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public static Mute getMuteById(int id) {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "mutes WHERE id='" + id + "'");

            if (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                MuteType type = MuteType.valueOf(set.getString("mute-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                return new Mute(uuid, username, type, reason, admin, id);
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public static List<Mute> getPermanentMutes() {
        List<Mute> mutes = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "mutes WHERE mute-type='perm'");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                MuteType type = MuteType.valueOf(set.getString("mute-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                mutes.add(new Mute(uuid, username, type, reason, admin, id));
            }
        } catch (SQLException ignored) {}
        return mutes;
    }

    public static List<Mute> getMutes() {
        List<Mute> mutes = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "mutes WHERE mute-type='mute'");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                MuteType type = MuteType.valueOf(set.getString("mute-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                mutes.add(new Mute(uuid, username, type, reason, admin, id));
            }
        } catch (SQLException ignored) {}
        return mutes;
    }

    public static List<Mute> getAllMutes() {
        List<Mute> mutes = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "mutes");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                MuteType type = MuteType.valueOf(set.getString("mute-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                mutes.add(new Mute(uuid, username, type, reason, admin, id));
            }
        } catch (SQLException ignored) {}
        return mutes;
    }

    public static List<Mute> getMutesByAdmin(String admin) {
        List<Mute> mutes = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "mutes WHERE admin='" + admin + "'");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                MuteType type = MuteType.valueOf(set.getString("mute-type"));
                String reason = set.getString("reason");
                int id = set.getInt("id");
                mutes.add(new Mute(uuid, username, type, reason, admin, id));
            }
        } catch (SQLException ignored) {}
        return mutes;
    }

    public static int getNextId() {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT id FROM " + PremiumPunishments.tablePrefix + "mutes");

            int id = 0;

            while (set.next()) {
                if (set.getInt("id") > id) id = set.getInt("id");
            }

            return id;
        } catch (SQLException ignored) {}
        return 0;
    }

}
