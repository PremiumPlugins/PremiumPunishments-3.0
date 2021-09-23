package com.exortions.premiumpunishments.commands.core.mute;

import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.util.MojangAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

@Usage(usage = "<player>")
@SuppressWarnings("ConstantConditions")
@Description(description = "Unmute a player. Unmuting a player will allow them to chat again if they were muted before unmuting them.")
public class UnmuteCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            boolean muted = checkMuted(args[0]);

            if (!muted) { sender.sendMessage(prefix() + ChatColor.RED + "That player is not currently muted!"); return; }

            MinecraftPlayer mp = MinecraftPlayerRepository.getPlayerByUuid(Objects.requireNonNull(MojangAPI.getUuidByName(args[0])));

            mp.setMuted(false);
            mp.setMuteexpirydate(new Timestamp(System.currentTimeMillis()));

            sender.sendMessage(prefix() + ChatColor.WHITE + "Successfully unmuted " + args[0] + "!");
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help unmute");
    }

    public boolean checkMuted(String name) {
        ResultSet set = database().query("SELECT username FROM mutes");

        boolean muted = false;

        try {
            while (set.next()) {
                if (set.getString("username").equalsIgnoreCase(name)) muted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return muted;
    }

}
