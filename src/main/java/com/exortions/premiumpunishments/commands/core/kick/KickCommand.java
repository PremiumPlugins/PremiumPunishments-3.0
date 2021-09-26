package com.exortions.premiumpunishments.commands.core.kick;

import com.exortions.pluginutils.command.CommandUtils;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.util.LogManager;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Usage(usage = "[-s] <player> <reason>")
@Description(description = "Kick a player with a custom message. Kicking a player will disconnect them from the server and display to them the custom message.")
public class KickCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            boolean silent = args[0].equals("-s");

            if (args.length >= 2 && !silent) {
                Player target = Bukkit.getPlayer(args[0]);
                String reason = CommandUtils.subStringArrayToString(args, 1);
                if (target == null) {
                    sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
                    return;
                }

                if (target.hasPermission("premiumpunishments.punishment-immune")) {
                    sender.sendMessage(prefix() + ChatColor.RED + "You cannot " + name() + " that player!");
                    return;
                }

                MinecraftPlayer mp = MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId());

                assert mp != null : args[0] + " is somehow not available. Unable to kick them!";
                mp.setKicks(mp.getKicks() + 1);
                target.kickPlayer(Placeholders.setKickPlaceholders(messages().get("kick-message"), reason, sender));

                if (sender instanceof Player) LogManager.addLog((Player) sender, "Kick", reason, target.getName(), "None");

                sender.sendMessage(prefix() + ChatColor.WHITE + "Successfully kicked " + target.getName());
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(Placeholders.setKickPlaceholders(messages().get("kick-broadcast-message"), reason, sender, args[0]));
                }
            } else if (args.length >= 3) {
                Player target = Bukkit.getPlayer(args[1]);
                String reason = CommandUtils.subStringArrayToString(args, 2);
                if (target == null) {
                    sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[1]));
                    return;
                }

                if (target.hasPermission("premiumpunishments.punishment-immune")) {
                    sender.sendMessage(prefix() + ChatColor.RED + "You cannot " + name() + " that player!");
                    return;
                }

                MinecraftPlayer mp = MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId());

                assert mp != null : args[1] + " is somehow not available. Unable to kick them!";
                mp.setKicks(mp.getKicks() + 1);
                target.kickPlayer(Placeholders.setKickPlaceholders(messages().get("kick-message"), reason, sender));

                if (sender instanceof Player) LogManager.addLog((Player) sender, "Kick", reason, target.getName(), "None");

                sender.sendMessage(prefix() + ChatColor.WHITE + "Successfully kicked " + target.getName());
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("premiumpunishments.staff-broadcasts")) player.sendMessage(Placeholders.setKickPlaceholders(messages().get("kick-broadcast-message"), reason, sender, args[1]));
                }
            } else Bukkit.dispatchCommand(sender, "premiumpunishments help kick");
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help kick");
    }

}