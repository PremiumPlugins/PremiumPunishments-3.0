package com.exortions.premiumpunishments.commands.core.kick;

import com.exortions.pluginutils.command.CommandUtils;
import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KickCommand implements SubCommand {
    @Override
    public String name() {
        return "kick";
    }

    @Override
    public String permission() {
        return "premiumpunishments.kick";
    }

    @Override
    public String usage() {
        return "/premiumpunishments kick <player> <reason>";
    }

    @Override
    public String description() {
        return "Kick a player with a custom message. Kicking a player will disconnect them from the server and display to them the custom message.";
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
        if (args.length >= 2) {
            Player target = Bukkit.getPlayer(args[0]);
            String reason = CommandUtils.subStringArrayToString(args, 1);
            if (target == null) {
                sender.sendMessage(prefix() + ChatColor.RED + "Could not find player by the name of " + args[0] + "!");
                return;
            }

            MinecraftPlayer mp = MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId());

            assert mp != null : args[0] + "is somehow not available. Unable to kick them!";
            mp.setKicks(mp.getKicks()+1);
            target.kickPlayer(Placeholders.setKickPlaceholders(messages().get("kick-message"), reason, sender));

            sender.sendMessage(prefix() + ChatColor.WHITE + "Successfully kicked " + target.getName());
        } else {
            Bukkit.dispatchCommand(sender, "premiumpunishments help kick");
        }
    }

}
