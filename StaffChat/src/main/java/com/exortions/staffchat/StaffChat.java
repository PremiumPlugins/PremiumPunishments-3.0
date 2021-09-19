package com.exortions.staffchat;

import com.exortions.pluginutils.chat.ChatUtils;
import com.exortions.pluginutils.config.Configuration;
import com.exortions.pluginutils.plugin.JavaVersion;
import com.exortions.pluginutils.plugin.MinecraftVersion;
import com.exortions.pluginutils.plugin.SpigotPlugin;
import com.exortions.staffchat.commands.StaffChatCommand;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.Objects;

public final class StaffChat extends SpigotPlugin {

    @Getter
    private static StaffChat plugin;

    @Getter @Setter
    private static Configuration configuration;

    private long ms;

    @Override
    public void onEnable() {
        ms = System.currentTimeMillis();

        plugin = this;

        sendStartupMessage();
        saveDefaultConfig();
        registerCommands();

        configuration = new Configuration(this, "config.yml");

        sendMessage(getPrefix() + "Successfully enabled StaffChat v" + getPluginVersion() + " in " + (System.currentTimeMillis() - ms) + "ms.");
    }

    @Override
    public void registerCommands() {
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new StaffChatCommand());
    }

    @Override
    public void registerListeners() {

    }

    private void sendStartupMessage() {
        sendMessage(ChatColor.DARK_PURPLE + "    __  " + ChatColor.GOLD + " __");
        sendMessage(ChatColor.DARK_PURPLE + "   |__  " + ChatColor.GOLD + "|      " + ChatColor.RESET + "StaffChat v" + getPluginVersion());
        sendMessage(ChatColor.DARK_PURPLE + "    __| " + ChatColor.GOLD + "|      " + ChatColor.RESET + "Running on Bukkit - CraftBukkit");
        sendMessage(ChatColor.DARK_PURPLE + "      " + ChatColor.GOLD + " __");
    }

    @Override
    public void onDisable() {
        ms = System.currentTimeMillis();

        sendMessage(getPrefix() + "Successfully disabled StaffChat v" + getPluginVersion() + " in " + (System.currentTimeMillis() - ms) + "ms.");
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
        return JavaVersion.JAVA_1_1;
    }

    public static String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "S" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "C" + ChatColor.GRAY + "] " + ChatColor.WHITE;
    }

    public static String getFormat() {
        return ChatUtils.colorize(configuration.getString("format"));
    }

}
