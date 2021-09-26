package com.exortions.premiumpunishments.objects.ban;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.BanType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class BanRepository {

    public static Ban getBanByUuid(UUID uuid) {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "bans WHERE uuid='" + uuid.toString() + "'");

            if (set.next()) {
                String username = set.getString("username");
                BanType type = BanType.valueOf(set.getString("ban-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                return new Ban(uuid.toString(), username, type, reason, admin, id);
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public static Ban getBanByUuid(String uuid) {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "bans WHERE uuid='" + uuid + "'");

            if (set.next()) {
                String username = set.getString("username");
                BanType type = BanType.valueOf(set.getString("ban-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                return new Ban(uuid, username, type, reason, admin, id);
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public static Ban getBanById(int id) {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "bans WHERE id='" + id + "'");

            if (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                BanType type = BanType.valueOf(set.getString("ban-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                return new Ban(uuid, username, type, reason, admin, id);
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public static List<Ban> getPermanentBans() {
        List<Ban> bans = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "bans WHERE ban-type='perm'");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                BanType type = BanType.valueOf(set.getString("ban-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                bans.add(new Ban(uuid, username, type, reason, admin, id));
            }
        } catch (SQLException ignored) {}
        return bans;
    }

    public static List<Ban> getBans() {
        List<Ban> bans = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "bans WHERE ban-type='ban'");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                BanType type = BanType.valueOf(set.getString("ban-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                bans.add(new Ban(uuid, username, type, reason, admin, id));
            }
        } catch (SQLException ignored) {}
        return bans;
    }

    public static List<Ban> getAllBans() {
        List<Ban> bans = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "bans");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                BanType type = BanType.valueOf(set.getString("ban-type"));
                String reason = set.getString("reason");
                String admin = set.getString("admin");
                int id = set.getInt("id");
                bans.add(new Ban(uuid, username, type, reason, admin, id));
            }
        } catch (SQLException ignored) {}
        return bans;
    }

    public static List<Ban> getBansByAdmin(String admin) {
        List<Ban> bans = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "bans WHERE admin='" + admin + "'");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                BanType type = BanType.valueOf(set.getString("ban-type"));
                String reason = set.getString("reason");
                int id = set.getInt("id");
                bans.add(new Ban(uuid, username, type, reason, admin, id));
            }
        } catch (SQLException ignored) {}
        return bans;
    }

    public static List<Ban> getBansByAdmin(UUID admin) {
        List<Ban> bans = new ArrayList<>();
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "bans WHERE admin='" + admin.toString() + "'");

            while (set.next()) {
                String uuid = set.getString("uuid");
                String username = set.getString("username");
                BanType type = BanType.valueOf(set.getString("ban-type"));
                String reason = set.getString("reason");
                int id = set.getInt("id");
                bans.add(new Ban(uuid, username, type, reason, admin.toString(), id));
            }
        } catch (SQLException ignored) {}
        return bans;
    }

    public static int getNextId() {
        try {
            ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT id FROM " + PremiumPunishments.tablePrefix + "bans");

            int id = 0;

            while (set.next()) {
                if (set.getInt("id") > id) id = set.getInt("id");
            }

            return id;
        } catch (SQLException ignored) {}
        return 0;
    }

}
