package com.exortions.premiumpunishments.commands.core.freeze;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.settings.Settings;
import com.exortions.premiumpunishments.util.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Usage(usage = "[-s] <player>")
@Description(description = "Freeze a player with a custom duration. Freezing a player prevents them from moving, breaking blocks, placing blocks, interacting with the world, and running chat commands. The player will remain frozen upon disconnecting and re-connecting to the server.")
public class FreezeCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 0) {
            boolean silent = args[0].equals("-s");

            if (args.length == 1 && !silent) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
                    return;
                }

                if (target.hasPermission("premiumpunishments.punishment-immune")) {
                    sender.sendMessage(prefix() + ChatColor.RED + "You cannot " + name() + " that player!");
                    return;
                }

                database().insertFrozenPlayer(target);

                if (sender instanceof Player) LogManager.addLog((Player) sender, "Freeze", "None", target.getName(), "None");

                sender.sendMessage(prefix() + "Successfully froze " + target.getName() + "!");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(messages().get("freeze-broadcast-message").replaceAll("%player%", args[0]).replaceAll("%admin%", sender.getName()));
                }
                target.sendMessage(messages().get("freeze-message"));
                if (!Settings.FREEZE_SPAM_MESSAGE) return;
                PremiumPunishments.frozenPlayers.put(target.getUniqueId(), new BukkitRunnable() {
                    @Override
                    public void run() {
                        target.sendMessage(messages().get("freeze-message"));
                    }
                }.runTaskTimer(PremiumPunishments.getPlugin(), Settings.FREEZE_SPAM_MESSAGE_DELAY, 0L));
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

                database().insertFrozenPlayer(target);

                if (sender instanceof Player) LogManager.addLog((Player) sender, "Freeze", "None", target.getName(), "None");

                sender.sendMessage(prefix() + "Successfully froze " + target.getName() + "!");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("premiumpunishments.staff-broadcasts")) player.sendMessage(messages().get("freeze-broadcast-message").replaceAll("%player%", args[1]).replaceAll("%admin%", sender.getName()));
                }
                target.sendMessage(messages().get("freeze-message"));
                if (!Settings.FREEZE_SPAM_MESSAGE) return;
                PremiumPunishments.frozenPlayers.put(target.getUniqueId(), new BukkitRunnable() {
                    @Override
                    public void run() {
                        target.sendMessage(messages().get("freeze-message"));
                    }
                }.runTaskTimer(PremiumPunishments.getPlugin(), Settings.FREEZE_SPAM_MESSAGE_DELAY, 0L));
            } else Bukkit.dispatchCommand(sender, "premiumpunishments help freeze");
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help freeze");
    }

}
