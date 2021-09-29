package com.exortions.premiumpunishments.commands.miscellaneous;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@Usage(usage = "info")
@Description(description = "View all info about the PremiumPunishments plugin.")
public class InfoCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        String prefix = PremiumPunishments.getPrefix();
        String message = prefix + ChatColor.WHITE + "Running " + ChatColor.RED + "PremiumPunishments v" + PremiumPunishments.getPlugin().getPluginVersion() + ChatColor.WHITE + ".";
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + "Spigot: https://www.spigotmc.org/resources/premium-punishments.96520/");
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + "Source code: https://www.github.com/PremiumPlugins/PremiumPunishments-3.0\n ");
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + "Created and developed by Exortions");
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + " - GitHub: https://www.github.com/Exortions");
        message = message.concat("\n" + ChatColor.RED + " > " + ChatColor.WHITE + " - Discord: Exortions#0001");
        sender.sendMessage(message);
    }
}
