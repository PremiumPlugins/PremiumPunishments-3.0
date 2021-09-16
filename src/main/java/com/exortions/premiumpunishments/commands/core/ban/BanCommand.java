package com.exortions.premiumpunishments.commands.core.ban;

import com.exortions.pluginutils.command.CommandUtils;
import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.BanType;
import com.exortions.premiumpunishments.objects.ban.BanRepository;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.objects.settings.Settings;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class BanCommand implements SubCommand {
    @Override
    public String name() {
        return "ban";
    }

    @Override
    public String permission() {
        return "premiumpunishments.ban";
    }

    @Override
    public String usage() {
        return "/premiumpunishments ban <player> <time> <reason>";
    }

    @Override
    public String description() {
        return "Ban a player with a custom duration and message. Banning a player will disconnect them from the server and display to them the custom message, as well as prevent them from re-connecting to the server until the duration has expired or they have been manually unbanned.";
    }

    @Override
    public List<String> tabcompletion() {
        List<String> ls = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) ls.add(player.getName());
        return ls;
    }

    @Override
    public boolean requiresPlayer() {
        return true;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length >= 3) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(prefix() + ChatColor.RED + "Could not find player by the name of " + args[0] + "!");
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
            if (mp.getBanned()) {
                player.sendMessage(prefix() + ChatColor.RED + "That player is already banned! They will be unbanned at: " + mp.getBanexpirydate().toString());
                return;
            }
            mp.setBanned(true);
            if (Settings.BAN_IP_ADDRESSES) database().insertBannedIp(player);
            if (!time.equals("-1")) {
                System.out.println("TEMP");
                database().insertNewBan(target.getUniqueId().toString(), target.getName(), BanType.ban, reason, player.getName(), BanRepository.getNextId());
                mp.setBanexpirydate(new Timestamp(System.currentTimeMillis() + (Placeholders.getTime("" + time))));
                target.kickPlayer(Placeholders.setBanPlaceholders(messages().get("ban-message"), BanRepository.getBanByUuid(target.getUniqueId())));
            } else {
                System.out.println("PERM");
                database().insertNewBan(target.getUniqueId().toString(), target.getName(), BanType.perm, reason, player.getName(), BanRepository.getNextId());
                mp.setBanexpirydate(new Timestamp(System.currentTimeMillis()));
                target.kickPlayer(Placeholders.setBanPlaceholders(messages().get("perm-ban-message"), BanRepository.getBanByUuid(target.getUniqueId())));
            }
            player.sendMessage(prefix() + "Successfully banned " + target.getName() + " for " + time + ".");
        } else {
            player.performCommand("premiumpunishments help ban");
        }
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        
    }
}
