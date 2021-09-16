package com.exortions.premiumpunishments.commands.core.mute;

import com.exortions.pluginutils.command.CommandUtils;
import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.MuteType;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.objects.mute.MuteRepository;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.List;

public class MuteCommand implements SubCommand {
    @Override
    public String name() {
        return "mute";
    }

    @Override
    public String permission() {
        return "premiumpunishments.mute";
    }

    @Override
    public String usage() {
        return "/premiumpunishments mute <player> <time> <reason>";
    }

    @Override
    public String description() {
        return "Mute a player with a custom duration. Muting a player prevents them from sending chat messages. The player will remain muted upon disconnecting and re-connecting to the server.";
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
        if (args.length >= 3) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(prefix() + ChatColor.RED + "Could not find player by the name of " + args[0] + "!");
                return;
            }
            String reason = "";
            int i = 0;
            for (String s : args) {
                if (i > 1) reason = reason.concat(s + " ");
                i++;
            }
            reason = reason.substring(0, reason.length()-1);
            String time = args[1];
            MinecraftPlayer mp = MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId());
            assert mp != null : "Player is somehow null!";
            if (mp.getMuted()) {
                sender.sendMessage(prefix() + ChatColor.RED + "That player is already muted! They will be unmuted at: " + mp.getMuteexpirydate().toString());
                return;
            }
            mp.setMuted(true);
            if (!time.equals("-1")) {
                database().insertNewMute(target.getUniqueId().toString(), target.getName(), MuteType.mute, reason, sender.getName(), MuteRepository.getNextId());
                mp.setMuteexpirydate(new Timestamp(System.currentTimeMillis() + (Placeholders.getTime(time))));
            } else {
                database().insertNewMute(target.getUniqueId().toString(), target.getName(), MuteType.perm, reason, sender.getName(), MuteRepository.getNextId());
                mp.setMuteexpirydate(new Timestamp(System.currentTimeMillis()));
            }
            sender.sendMessage(prefix() + "Successfully muted " + target.getName() + " for " + time + ".");
        } else {
            Bukkit.dispatchCommand(sender, "premiumpunishments help mute");
        }
    }
}
