package com.exortions.premiumpunishments.util;

import com.exortions.pluginutils.database.DatabaseManipulator;
import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.BanType;
import com.exortions.premiumpunishments.enums.MuteType;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class DatabaseHandler extends DatabaseManipulator {

    @Getter
    public final String tablePrefix;

    public DatabaseHandler(String database, String host, String port, String username, String password, String tablePrefix) {
        super(database, host, port, username, password, false);

        this.tablePrefix = tablePrefix;
        PremiumPunishments.tablePrefix = tablePrefix;
    }

    public void createPlayersTable() {
        sendMessage(getPrefix() + " - Creating table \"" + tablePrefix + "players\"...");
        try {
            DatabaseMetaData meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, tablePrefix + "players", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"" + tablePrefix + "players\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"" + tablePrefix + "players\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`" + tablePrefix + "players` (" +
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
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"" + tablePrefix + "players\"");
        }
    }

    public void createBansTable() {
        sendMessage(getPrefix() + " - Creating table \"" + tablePrefix + "bans\"...");
        try {
            DatabaseMetaData meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, tablePrefix + "bans", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"" + tablePrefix + "bans\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"" + tablePrefix + "bans\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`" + tablePrefix + "bans` (" +
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
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"" + tablePrefix + "bans\"");
        }
    }

    public void createMutesTable() {
        sendMessage(getPrefix() + " - Creating table \"" + tablePrefix + "mutes\"...");
        try {
            DatabaseMetaData meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, tablePrefix + "mutes", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"" + tablePrefix + "mutes\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"" + tablePrefix + "mutes\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`" + tablePrefix + "mutes` (" +
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
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"" + tablePrefix + "\"");
        }
    }

    public void createBannedIpsTable() {
        sendMessage(getPrefix() + " - Creating table \"" + tablePrefix + "banned_ips\"");
        try {
            DatabaseMetaData  meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, tablePrefix + "banned_ips", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"" + tablePrefix + "banned_ips\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"" + tablePrefix + "banned_ips\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`" + tablePrefix + "banned_ips` (" +
                        "  `uuid` VARCHAR(100) NOT NULL," +
                        "  `ip` VARCHAR(100) NOT NULL)";

                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"" + tablePrefix + "banned_ips\"");
        }
    }

    public void createFrozenPlayersTable() {
        sendMessage(getPrefix() + " - Creating table \"" + tablePrefix + "frozen_players\"");
        try {
            DatabaseMetaData  meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, tablePrefix + "frozen_players", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"" + tablePrefix + "frozen_players\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"" + tablePrefix + "frozen_players\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`" + tablePrefix + "frozen_players` (" +
                        "  `uuid` VARCHAR(100) NOT NULL)";

                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"" + tablePrefix + "frozen_players\"");
        }
    }

    public void createNotesTable() {
        sendMessage(getPrefix() + " - Creating table \"" + tablePrefix + "notes\"");
        try {
            DatabaseMetaData  meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, tablePrefix + "notes", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"" + tablePrefix + "notes\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"" + tablePrefix + "notes\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`" + tablePrefix + "notes` (" +
                        "  `uuid` VARCHAR(100) NOT NULL," +
                        "  `name` VARCHAR(100) NOT NULL," +
                        "  `target` VARCHAR(100) NOT NULL)";

                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"" + tablePrefix + "notes\"");
        }
    }

    public void createLogsTable() {
        sendMessage(getPrefix() + " - Creating table \"" + tablePrefix + "logs\"");
        try {
            DatabaseMetaData  meta = getConnection().getMetaData();
            ResultSet set = meta.getTables(null, null, tablePrefix + "logs", null);
            if (set.next()) sendMessage(getPrefix() + " - Table \"" + tablePrefix + "logs\" already exists!"); else {
                sendMessage(getPrefix() + " - Table \"" + tablePrefix + "logs\" does not exist, creating it now!");

                String sql = "CREATE TABLE `" + getDatabase() + "`.`" + tablePrefix + "logs` (" +
                        "  `uuid` VARCHAR(100) NOT NULL," +
                        "  `type` VARCHAR(45) NOT NULL," +
                        "  `reason` VARCHAR(300) NOT NULL," +
                        "  `target` VARCHAR(45) NOT NULL," +
                        "  `username` VARCHAR(45) NOT NULL," +
                        "  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "  `time` VARCHAR(45) NOT NULL);";

                execute(sql);
            }
        } catch (SQLException e) {
            sendMessage(getPrefix() + ChatColor.RED + " - An error occurred while trying to create table \"" + tablePrefix + "logs\"");
        }
    }

    public void insertNewPlayer(UUID uuid, String username) {
        String sql = "INSERT INTO" +
                " `" + getDatabase() + "`.`" + tablePrefix + "players`(" +
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
                " `" + getDatabase() + "`.`" + tablePrefix + "players`(" +
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
        String sql = "INSERT INTO `" + getDatabase() + "`.`" + tablePrefix + "bans`" +
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
        String sql = "INSERT INTO `" + getDatabase() + "`.`" + tablePrefix + "mutes`" +
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
        String sql = "INSERT INTO `" + getDatabase() + "`.`" + tablePrefix + "banned_ips`" +
                "(`uuid`," +
                "`ip`)" +
                "VALUES(" +
                "'" + player.getUniqueId() + "'," +
                "'" + Objects.requireNonNull(player.getAddress()).getHostName() + "'" +
                ");";
        execute(sql);
    }

    public void insertFrozenPlayer(Player player) {
        String sql = "INSERT INTO `" + getDatabase() + "`.`" + tablePrefix + "frozen_players`" +
                "(`uuid`)" +
                "VALUES(" +
                "'" + player.getUniqueId() + "');";
        execute(sql);
    }

    public void insertNote(UUID uuid, String name, String target) {
        String sql = "INSERT INTO `" + getDatabase() + "`.`" + tablePrefix + "notes`" +
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
