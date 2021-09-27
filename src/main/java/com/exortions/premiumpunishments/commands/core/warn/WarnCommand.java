package com.exortions.premiumpunishments.commands.core.warn;

import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.util.LogManager;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@Usage(usage = "[-s] <player> <reason>")
@Description(description = "Warn a player with a custom message. Warning a player will send them the custom message as a formal warning.")
public class WarnCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            boolean silent = args[0].equals("-s");

            if (args.length > 1 && !silent) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target != null) {

                    if (target.hasPermission("premiumpunishments.punishment-immune")) {
                        sender.sendMessage(prefix() + ChatColor.RED + "You cannot " + name() + " that player!");
                        return;
                    }

                    String reason = "";
                    int i = 0;
                    for (String s : args) {
                        if (i > 0) reason = reason.concat(s + " ");
                        i++;
                    }
                    reason = reason.substring(0, reason.length() - 1);

                    Objects.requireNonNull(MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId())).setWarns(Objects.requireNonNull(MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId())).getWarns());

                    target.sendMessage(Placeholders.setWarnPlaceholders(messages().get("warn-message"), reason, sender));

                    if (sender instanceof Player) LogManager.addLog((Player) sender, "Warn", reason, args[0], "None");

                    sender.sendMessage(prefix() + "Successfully warned " + target.getName() + " for " + reason + "!");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Placeholders.setWarnPlaceholders(messages().get("warn-broadcast-message"), reason, sender, args[0]));
                    }
                } else sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
            } else if (args.length >= 2) {
                Player target = Bukkit.getPlayer(args[1]);

                if (target != null) {

                    if (target.hasPermission("premiumpunishments.punishment-immune")) {
                        sender.sendMessage(prefix() + ChatColor.RED + "You cannot " + name() + " that player!");
                        return;
                    }

                    String reason = "";
                    int i = 0;
                    for (String s : args) {
                        if (i > 1) reason = reason.concat(s + " ");
                        i++;
                    }
                    reason = reason.substring(0, reason.length() - 1);

                    Objects.requireNonNull(MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId())).setWarns(Objects.requireNonNull(MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId())).getWarns());

                    target.sendMessage(Placeholders.setWarnPlaceholders(messages().get("warn-message"), reason, sender));

                    if (sender instanceof Player) LogManager.addLog((Player) sender, "Warn", reason, args[1], "None");

                    sender.sendMessage(prefix() + "Successfully warned " + target.getName() + " for " + reason + "!");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.hasPermission("premiumpunishments.staff-broadcasts")) player.sendMessage(Placeholders.setWarnPlaceholders(messages().get("warn-broadcast-message"), reason, sender, args[1]));
                    }
                } else sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[1]));
            }
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help warn");
    }

}
