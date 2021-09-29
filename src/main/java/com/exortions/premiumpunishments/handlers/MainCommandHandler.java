package com.exortions.premiumpunishments.handlers;

import com.exortions.premiumpunishments.PremiumPunishments;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String name = command.getName().toLowerCase();
        for (String cmd : PremiumPunishments.mainCommands) if (name.equals(cmd)) run(sender, cmd, args);
        return false;
    }

    private void run(CommandSender sender, String name, String[] args) {
        String s = "premiumpunishments " + name + " ";
        for (String arg : args) s = s.concat(arg + " ");
        Bukkit.dispatchCommand(sender, s);
    }

}
