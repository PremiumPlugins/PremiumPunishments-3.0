package com.exortions.premiumpunishments.commands.core.mute;

import com.exortions.premiumpunishments.enums.MuteType;
import com.exortions.premiumpunishments.objects.ban.BanRepository;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.objects.mute.MuteRepository;
import com.exortions.premiumpunishments.util.LogManager;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Objects;

@Usage(usage = "[-s] <player> <time> <reason>")
@Description(description = "Mute a player with a custom duration. Muting a player prevents them from sending chat messages. The player will remain muted upon disconnecting and re-connecting to the server.")
public class MuteCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        boolean silent = false;

        if (args.length != 0) {

            if (args[0].equals("-s")) silent = true;

            if (!silent) {
                if (args.length >= 3) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
                        return;
                    }

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
                    String time = args[1];
                    if (!mute(sender, target, reason, time)) return;

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (time.equals("-1"))
                            player.sendMessage(Placeholders.setMutePlaceholders(messages().get("perm-mute-broadcast-message"), Objects.requireNonNull(MuteRepository.getMuteByUuid(target.getUniqueId()))));
                        else
                            player.sendMessage(Placeholders.setMutePlaceholders(messages().get("mute-broadcast-message"), Objects.requireNonNull(MuteRepository.getMuteByUuid(target.getUniqueId()))));
                    }
                } else {
                    Bukkit.dispatchCommand(sender, "premiumpunishments help mute");
                }
            } else {
                if (args.length >= 4) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[1]));
                        return;
                    }

                    if (target.hasPermission("premiumpunishments.punishment-immune")) {
                        sender.sendMessage(prefix() + ChatColor.RED + "You cannot " + name() + " that player!");
                        return;
                    }

                    String reason = "";
                    int i = 0;
                    for (String s : args) {
                        if (i > 2) reason = reason.concat(s + " ");
                        i++;
                    }
                    reason = reason.substring(0, reason.length() - 1);
                    String time = args[2];

                    if (!mute(sender, target, reason, time)) return;

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.hasPermission("premiumpunishments.staff-broadcasts")) {
                            if (time.equals("-1"))
                                player.sendMessage(Placeholders.setMutePlaceholders(messages().get("perm-mute-broadcast-message") + ChatColor.WHITE + " [SILENT]", Objects.requireNonNull(MuteRepository.getMuteByUuid(target.getUniqueId()))));
                            else
                                player.sendMessage(Placeholders.setMutePlaceholders(messages().get("mute-broadcast-message") + ChatColor.WHITE + " [SILENT]", Objects.requireNonNull(MuteRepository.getMuteByUuid(target.getUniqueId()))));
                        }
                    }
                }
            }
        } else {
            Bukkit.dispatchCommand(sender, "premiumpunishments help mute");
        }
    }

    private boolean mute(CommandSender sender, Player target, String reason, String time) {
        MinecraftPlayer mp = MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId());
        assert mp != null : "Player is somehow null!";
        if (mp.getMuted()) {
            sender.sendMessage(prefix() + ChatColor.RED + "That player is already muted! They will be unmuted at: " + mp.getMuteexpirydate().toString());
            return false;
        }
        mp.setMuted(true);
        if (!time.equals("-1")) {
            database().insertNewMute(target.getUniqueId().toString(), target.getName(), MuteType.mute, reason, sender.getName(), MuteRepository.getNextId());
            mp.setMuteexpirydate(new Timestamp(System.currentTimeMillis() + (Placeholders.getTime(time))));
        } else {
            database().insertNewMute(target.getUniqueId().toString(), target.getName(), MuteType.perm, reason, sender.getName(), MuteRepository.getNextId());
            mp.setMuteexpirydate(new Timestamp(System.currentTimeMillis()));
        }

        if (sender instanceof Player) LogManager.addLog((Player) sender, "Mute", reason, target.getName(), time);

        sender.sendMessage(prefix() + "Successfully muted " + target.getName() + " for " + time + ".");
        return true;
    }

}
