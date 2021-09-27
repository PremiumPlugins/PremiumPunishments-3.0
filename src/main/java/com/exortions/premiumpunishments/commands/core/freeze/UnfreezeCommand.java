package com.exortions.premiumpunishments.commands.core.freeze;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.util.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Usage(usage = "[-s] <player>")
@Description(description = "Unfreeze a player. Unfreezing a player will allow the player to continue playing on the server like normal before they were frozen.")
public class UnfreezeCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
                    return;
                }

                if (target.hasPermission("premiumpunishments.punishment-immune")) {
                    sender.sendMessage(prefix() + ChatColor.RED + "You cannot " + name() + " that player!");
                    return;
                }

                database().execute("DELETE FROM `" + database().getDatabase() + "`.`" + PremiumPunishments.tablePrefix + "frozen_players` WHERE uuid='" + target.getUniqueId() + "';");

                PremiumPunishments.frozenPlayers.get(target.getUniqueId()).cancel();
                PremiumPunishments.frozenPlayers.remove(target.getUniqueId());

                if (sender instanceof Player) LogManager.addLog((Player) sender, "Unfreeze", "None", target.getName(), "None");

                sender.sendMessage(prefix() + "Successfully unfroze " + target.getName());
                for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage(messages().get("unfreeze-broadcast-message").replaceAll("%admin%", sender.getName()).replaceAll("%player%", args[0]));
            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[1]));
                    return;
                }

                if (target.hasPermission("premiumpunishments.punishment-immune")) {
                    sender.sendMessage(prefix() + ChatColor.RED + "You cannot " + name() + " that player!");
                    return;
                }

                database().execute("DELETE FROM `" + database().getDatabase() + "`.`" + PremiumPunishments.tablePrefix + "frozen_players` WHERE uuid='" + target.getUniqueId() + "';");

                PremiumPunishments.frozenPlayers.get(target.getUniqueId()).cancel();
                PremiumPunishments.frozenPlayers.remove(target.getUniqueId());

                if (sender instanceof Player) LogManager.addLog((Player) sender, "Unfreeze", "None", target.getName(), "None");

                sender.sendMessage(prefix() + "Successfully unfroze " + target.getName());
                for (Player player : Bukkit.getOnlinePlayers()) if (player.hasPermission("premiumpunishments.staff-broadcasts")) player.sendMessage((messages().get("unfreeze-broadcast-message") + ChatColor.WHITE + " [SILENT]").replaceAll("%admin%", sender.getName()).replaceAll("%player%", args[1]));
            } else Bukkit.dispatchCommand(sender, "premiumpunishments help unfreeze");
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help unfreeze");
    }
}
