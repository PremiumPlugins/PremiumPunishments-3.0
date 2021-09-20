package com.exortions.premiumpunishments;

import com.exortions.pluginutils.chat.ChatUtils;
import com.exortions.pluginutils.command.subcommand.SubCommandHandler;
import com.exortions.pluginutils.config.Configuration;
import com.exortions.pluginutils.plugin.JavaVersion;
import com.exortions.pluginutils.plugin.MinecraftVersion;
import com.exortions.pluginutils.plugin.SpigotPlugin;
import com.exortions.premiumpunishments.handlers.CommandHandler;
import com.exortions.premiumpunishments.handlers.TabCompleteHandler;
import com.exortions.premiumpunishments.listeners.FreezeListener;
import com.exortions.premiumpunishments.listeners.PlayerChatListener;
import com.exortions.premiumpunishments.listeners.PlayerJoinListener;
import com.exortions.premiumpunishments.objects.settings.Settings;
import com.exortions.premiumpunishments.util.DatabaseHandler;
import lombok.Getter;
import lombok.Setter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"UnusedAssignment", "ConstantConditions"})
public final class PremiumPunishments extends SpigotPlugin {

    @Getter
    private static PremiumPunishments plugin;

    @Getter
    private SubCommandHandler commandHandler;
    @Getter @Setter
    private Configuration configuration;
    @Getter
    public DatabaseHandler database;

    @Getter
    private HashMap<String, String> messages;

    @Getter
    private HashMap<String, String> databaseInformation;

    public static HashMap<UUID, BukkitTask> frozenPlayers;

    private long ms;

    @Override
    public void onEnable() {
        boolean loaded = false;
        ms = System.currentTimeMillis();

        plugin = this;

        sendStartupMessage();

        registerListeners();
        registerCommands();

        if (!loadConfiguration()) return; else loaded = true;
        if (!loadMessages()) return; else loaded = true;
        if (!loadStorage()) return; else loaded = true;
        if (!loadData()) return; else loaded = true;
        if (!loadMetrics()) return; else loaded = true;

        while (loaded) {
            sendMessage(getPrefix() + "Successfully enabled PremiumPunishments v" + getPluginVersion() + " in " + (System.currentTimeMillis() - ms) + "ms.");
            loaded = false;
        }
    }

    @Override
    public void registerListeners() {
        sendMessage(getPrefix() + "Registering listeners...");

        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new FreezeListener(), this);
    }

    @Override
    public void registerCommands() {
        sendMessage(getPrefix() + "Registering commands...");

        // Register main command
        PluginCommand pp = getCommand("premiumpunishments");
        if (pp == null) {
            sendMessage(getPrefix() + ChatColor.RED + " - Could not get main command /premiumpunishments registered with the server!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        commandHandler = new CommandHandler();
        pp.setExecutor(commandHandler);
        pp.setTabCompleter(new TabCompleteHandler());
    }

    private boolean loadConfiguration() {
        sendMessage(getPrefix() + "Loading configuration...");
        saveDefaultConfig();

        configuration = new Configuration(this, "config.yml");

        new Settings(
                configuration.getBoolean("settings.commands.ban.ip-ban"),
                configuration.getBoolean("settings.commands.freeze.disable-movement"),
                configuration.getBoolean("settings.commands.freeze.disable-interactions"),
                configuration.getBoolean("settings.commands.freeze.disable-chatting"),
                configuration.getStringList("settings.commands.freeze.disabled-commands"),
                configuration.getBoolean("settings.commands.freeze.spam-message"),
                configuration.getInt("settings.commands.freeze.spam-message-delay"));

        sendMessage(getPrefix() + "Settings:");
        sendMessage(getPrefix() + " - Ban IP addresses: " + Settings.BAN_IP_ADDRESSES);
        sendMessage(getPrefix());
        sendMessage(getPrefix() + " - Disable movement (Freeze): " + Settings.FREEZE_DISABLE_MOVEMENT);
        sendMessage(getPrefix() + " - Disable interactions (Freeze): " + Settings.FREEZE_DISABLE_INTERACTIONS);
        sendMessage(getPrefix() + " - Disable chatting (Freeze): " + Settings.FREEZE_DISABLE_CHATTING);
        sendMessage(getPrefix() + " - Disabled commands (Freeze): " + Collections.singletonList(Settings.FREEZE_DISABLED_COMMANDS));

        return true;
    }

    private boolean loadMessages() {
        sendMessage(getPrefix() + "Loading messages...");

        messages = new HashMap<>();

        messages.put("ban-message", ChatUtils.colorize(configuration.getString("messages.punishments.ban-message")));
        messages.put("perm-ban-message", ChatUtils.colorize(configuration.getString("messages.punishments.perm-ban-message")));
        messages.put("mute-message", ChatUtils.colorize(configuration.getString("messages.punishments.mute-message")));
        messages.put("perm-mute-message", ChatUtils.colorize(configuration.getString("messages.punishments.perm-mute-message")));

        messages.put("kick-message", ChatUtils.colorize(configuration.getString("messages.punishments.kick-message")));
        messages.put("warn-message", ChatUtils.colorize(configuration.getString("messages.punishments.warn-message")));

        messages.put("freeze-message", ChatUtils.colorize(configuration.getString("messages.punishments.freeze-message")));

        messages.put("only-players", ChatUtils.colorize(configuration.getString("messages.commands.only-players").replaceAll("%prefix%", getPrefix())));
        messages.put("no-permission", ChatUtils.colorize(configuration.getString("messages.commands.no-permission").replaceAll("%prefix%", getPrefix())));
        messages.put("unknown-command", ChatUtils.colorize(configuration.getString("messages.commands.unknown-command").replaceAll("%prefix%", getPrefix())));

        messages.put("unknown-player", ChatUtils.colorize(configuration.getString("messages.commands.unknown-player").replaceAll("%prefix%", getPrefix())));

        return true;
    }

    private boolean loadStorage() {
        sendMessage(getPrefix() + "Loading storage provider...");

        loadDatabaseInformation();

        database = new DatabaseHandler(databaseInformation.get("database"), databaseInformation.get("host"), databaseInformation.get("port"), databaseInformation.get("username"), databaseInformation.get("password"));

        sendMessage(getPrefix() + "Creating connection to database...");
        try {
            database.createConnection();
            sendMessage(getPrefix() + " - Successfully connected to database!");
        } catch (SQLException e) {
            sendMessage(getPrefix() + " - An error occurred while trying to connect to the database! Check the config.yml and restart your server.");
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    private void loadDatabaseInformation() {
        databaseInformation = new HashMap<>();

        databaseInformation.put("database", configuration.getString("database.database"));
        databaseInformation.put("host", configuration.getString("database.host"));
        databaseInformation.put("port", configuration.getString("database.port"));

        databaseInformation.put("username", configuration.getString("database.username"));
        databaseInformation.put("password", configuration.getString("database.password"));
    }

    private boolean loadData() {
        sendMessage(getPrefix() + "Performing initial data load...");

        frozenPlayers = new HashMap<>();

        database.createFrozenPlayersTable();
        database.createBannedIpsTable();
        database.createPlayersTable();
        database.createMutesTable();
        database.createNotesTable();
        database.createBansTable();

        return true;
    }

    private boolean loadMetrics() {
        sendMessage(getPrefix() + "Loading bStat metrics...");

        new Metrics(this, 12756);

        return true;
    }

    private void sendStartupMessage() {
        sendMessage(ChatColor.RED + "    __   " + ChatColor.GOLD + "__");
        sendMessage(ChatColor.RED + "   |__) " + ChatColor.GOLD +"|__)   " + ChatColor.RESET + "PremiumPunishments v" + getPluginVersion());
        sendMessage(ChatColor.RED + "   |    " + ChatColor.GOLD + "|      " + ChatColor.RESET + "Running on Bukkit - CraftBukkit");
        sendMessage(ChatColor.RED + "        ");
    }

    @Override
    public void onDisable() {
        ms = System.currentTimeMillis();

        sendMessage(getPrefix() + "Successfully disabled PremiumPunishments v" + getPluginVersion() + " in " + (System.currentTimeMillis() - ms) + "ms.");
    }

    public void reload() {
        onDisable();
        onEnable();
    }
    
    @Override
    public String getPluginName() {
        return getDescription().getName();
    }

    @Override
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    @Override
    public MinecraftVersion getPluginMinecraftVersion() {
        return MinecraftVersion.MINECRAFT_1_16_5;
    }

    @Override
    public JavaVersion getJavaVersion() {
        return JavaVersion.JAVA_1_8;
    }

    public static String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.RED + "" + ChatColor.BOLD + "P" + ChatColor.GOLD + "" + ChatColor.BOLD + "P" + ChatColor.GRAY + "] " + ChatColor.WHITE;
    }

}
