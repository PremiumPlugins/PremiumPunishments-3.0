package com.exortions.staffchat.commands;

import com.exortions.staffchat.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StaffChat.getPrefix() + ChatColor.RED + "Only players can use this command!");
            return true;
        }

        if (!sender.hasPermission("staffchat.chat")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
            return true;
        }

        if (args.length != 0) {
            String message = "";
            for (String s : args) message = message.concat(s + " ");
            message = message.substring(0, message.length()-1);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("staffchat.chat")) {
                    player.sendMessage(StaffChat.getFormat().replaceAll("%player%", sender.getName()).replaceAll("%message%", message));
                }
            }
        } else {
            sender.sendMessage(StaffChat.getPrefix() + ChatColor.RED + "Incorrect usage! Use /staffchat <message>.");
            return true;
        }
        return false;
    }
}
