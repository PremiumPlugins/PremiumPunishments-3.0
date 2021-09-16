package com.exortions.premiumpunishments.commands.miscellaneous;

import com.exortions.pluginutils.command.subcommand.SubCommand;
import com.exortions.premiumpunishments.PremiumPunishments;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoCommand implements SubCommand {
    @Override
    public String name() {
        return "info";
    }

    @Override
    public String permission() {
        return "premiumpunishments.info";
    }

    @Override
    public String usage() {
        return "/premiumpunishments info";
    }

    @Override
    public String description() {
        return "View all info about the PremiumPunishments plugin.";
    }

    @Override
    public List<String> tabcompletion() {
        return null;
    }

    @Override
    public boolean requiresPlayer() {
        return false;
    }

    @Override
    public void execute(Player player, String[] args) {

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String prefix = PremiumPunishments.getPrefix();
        String message = prefix + ChatColor.WHITE + "Running " + ChatColor.RED + "PremiumPunishments v" + PremiumPunishments.getPlugin().getPluginVersion() + ChatColor.WHITE + ".";
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + "Spigot: https://www.spigotmc.org/");
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + "Code: https://www.github.com/Exortions/PremiumPunishments\n ");
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + "Created and developed by Exortions");
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + " - GitHub: https://www.github.com/Exortions");
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + " - Discord: Exortions#0001");
        sender.sendMessage(message);
    }
}
