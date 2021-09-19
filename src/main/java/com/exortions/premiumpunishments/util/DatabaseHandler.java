package com.exortions.premiumpunishments.util;

import com.exortions.pluginutils.database.DatabaseManipulator;
import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.BanType;
import com.exortions.premiumpunishments.enums.MuteType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class DatabaseHandler extends DatabaseManipulator {

    public DatabaseHandler(String database, String host, String port, String username, String password) {
        super(database, host, port, username, password, false);
    }

    public void createPlayersTable() {
        sendMessage(getPrefix() + " - Creating table \"players\"...");
        try {
            DatabaseMetaData meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, "players", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"players\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"players\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`players` (" +
                        "  `uuid` VARCHAR(100) NOT NULL," +
                        "  `username` VARCHAR(100) NOT NULL," +
                        "  `banned` VARCHAR(100) NOT NULL," +
                        "  `banexpirydate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "  `muted` VARCHAR(100) NOT NULL," +
                        "  `muteexpirydate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "  `warns` INT NOT NULL," +
                        "  `kicks` INT NOT NULL," +
                        "  PRIMARY KEY (`uuid`, `username`)," +
                        "  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE);";
                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"bans\"");
        }
    }

    public void createBansTable() {
        sendMessage(getPrefix() + " - Creating table \"bans\"...");
        try {
            DatabaseMetaData meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, "bans", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"bans\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"bans\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`bans` (" +
                        "  `uuid` VARCHAR(100) NOT NULL," +
                        "  `username` VARCHAR(100) NOT NULL," +
                        "  `ban-type` VARCHAR(100) NOT NULL," +
                        "  `reason` VARCHAR(100) NOT NULL," +
                        "  `admin` VARCHAR(100) NOT NULL," +
                        "  `id` INT NOT NULL," +
                        "  PRIMARY KEY (`uuid`)," +
                        "  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE" +
                        ");";
                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"bans\"");
        }
    }

    public void createMutesTable() {
        sendMessage(getPrefix() + " - Creating table \"mutes\"...");
        try {
            DatabaseMetaData meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, "mutes", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"mutes\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"mutes\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`mutes` (" +
                        "  `uuid` VARCHAR(100) NOT NULL," +
                        "  `username` VARCHAR(100) NOT NULL," +
                        "  `mute-type` VARCHAR(100) NOT NULL," +
                        "  `reason` VARCHAR(100) NOT NULL," +
                        "  `admin` VARCHAR(45) NOT NULL," +
                        "  `id` INT NOT NULL," +
                        "  PRIMARY KEY (`uuid`))";
                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"mutes\"");
        }
    }

    public void createBannedIpsTable() {
        sendMessage(getPrefix() + " - Creating table \"banned_ips\"");
        try {
            DatabaseMetaData  meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, "banned_ips", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"banned_ips\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"banned_ips\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`banned_ips` (" +
                        "  `uuid` VARCHAR(100) NOT NULL," +
                        "  `ip` VARCHAR(100) NOT NULL)";

                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"banned_ips\"");
        }
    }

    public void createFrozenPlayersTable() {
        sendMessage(getPrefix() + " - Creating table \"frozen_players\"");
        try {
            DatabaseMetaData  meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, "frozen_players", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"frozen_players\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"frozen_players\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`frozen_players` (" +
                        "  `uuid` VARCHAR(100) NOT NULL)";

                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"frozen_players\"");
        }
    }

    public void createNotesTable() {
        sendMessage(getPrefix() + " - Creating table \"notes\"");
        try {
            DatabaseMetaData  meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, "notes", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"notes\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"notes\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`notes` (" +
                        "  `uuid` VARCHAR(100) NOT NULL," +
                        "  `name` VARCHAR(100) NOT NULL," +
                        "  `target` VARCHAR(100) NOT NULL)";

                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"notes\"");
        }
    }

    public void insertNewPlayer(UUID uuid, String username) {
        String sql = "INSERT INTO" +
                " `" + getDatabase() + "`.`players`(" +
                "`uuid`," +
                "`username`," +
                "`banned`," +
                "`banexpirydate`," +
                "`muted`," +
                "`muteexpirydate`," +
                "`warns`," +
                "`kicks`" +
                ")" +
                "VALUES(" +
                "'" + uuid.toString() + "'," +
                "'" + username + "'," +
                "'false','" +
                new Timestamp(System.currentTimeMillis()) + "'," +
                "'false','" +
                new Timestamp(System.currentTimeMillis()) + "'," +
                "'0'," +
                "'0'" +
                ");";
        execute(sql);
    }

    public void insertPlayer(String uuid, String username, boolean banned, Timestamp banexpirydate, boolean muted, Timestamp muteexpirydate, int warns, int kicks) {
        String sql = "INSERT INTO" +
                " `" + getDatabase() + "`.`players`(" +
                "`uuid`," +
                "`username`," +
                "`banned`," +
                "`banexpirydate`," +
                "`muted`," +
                "`muteexpirydate`," +
                "`warns`," +
                "`kicks`" +
                ")" +
                "VALUES(" +
                "'" + uuid + "'," +
                "'" + username + "'," +
                "'" + banned + "','" +
                banexpirydate + "'," +
                "'" + muted + "','" +
                muteexpirydate + "'," +
                "'" + warns + "'," +
                "'" + kicks + "'" +
                ");";
        execute(sql);
    }

    public void insertNewBan(String uuid, String username, BanType type, String reason, String admin, int id) {
        String sql = "INSERT INTO `" + getDatabase() + "`.`bans`" +
                "(`uuid`," +
                "`username`," +
                "`ban-type`," +
                "`reason`," +
                "`admin`," +
                "`id`)" +
                "VALUES(" +
                "'" + uuid + "'," +
                "'" + username + "'," +
                "'" + type.getType() + "'," +
                "'" + reason + "'," +
                "'" + admin + "'," +
                "'" + id + "'" +
                ");";
        execute(sql);
    }

    public void insertNewMute(String uuid, String username, MuteType type, String reason, String admin, int id) {
        String sql = "INSERT INTO `" + getDatabase() + "`.`mutes`" +
                "(`uuid`," +
                "`username`," +
                "`mute-type`," +
                "`reason`," +
                "`admin`," +
                "`id`)" +
                "VALUES(" +
                "'" + uuid + "'," +
                "'" + username + "'," +
                "'" + type.getType() + "'," +
                "'" + reason + "'," +
                "'" + admin + "'," +
                "'" + id + "'" +
                ");";
        execute(sql);
    }

    public void insertBannedIp(Player player) {
        String sql = "INSERT INTO `" + getDatabase() + "`.`banned_ips`" +
                "(`uuid`," +
                "`ip`)" +
                "VALUES(" +
                "'" + player.getUniqueId() + "'," +
                "'" + Objects.requireNonNull(player.getAddress()).getHostName() + "'" +
                ");";
        execute(sql);
    }

    public void insertFrozenPlayer(Player player) {
        String sql = "INSERT INTO `" + getDatabase() + "`.`frozen_players`" +
                "(`uuid`)" +
                "VALUES(" +
                "'" + player.getUniqueId() + "');";
        execute(sql);
    }

    public void insertNote(UUID uuid, String name, String target) {
        String sql = "INSERT INTO `" + getDatabase() + "`.`notes`" +
                "(`uuid`," +
                "`name`," +
                "`target`)" +
                "VALUES(" +
                "'" + uuid.toString() + "'," +
                "'" + name + "'," +
                "'" + target + "'" +
                ")";
        execute(sql);
    }

    private void sendMessage(String s) {
        PremiumPunishments.getPlugin().sendMessage(s);
    }

    private String getPrefix() {
        return PremiumPunishments.getPrefix();
    }

}
