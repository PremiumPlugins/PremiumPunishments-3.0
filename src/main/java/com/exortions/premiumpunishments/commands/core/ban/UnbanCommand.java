package com.exortions.premiumpunishments.commands.core.ban;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.util.LogManager;
import com.exortions.premiumpunishments.util.MojangAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@Usage(usage = "[-s] <player>")
@SuppressWarnings("ConstantConditions")
@Description(description = "Unban a player. Unbanning a player will revoke all ban punishments on them, and will allow them to join the server if they were banned before unbanning them.")
public class UnbanCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (unban(sender, args, false)) return;

            for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(messages().get("unban-broadcast-message").replaceAll("%admin%", sender.getName()).replaceAll("%player%", args[0]));
        } else if (args.length == 2) {
            if (!args[0].equals("-s")) { Bukkit.dispatchCommand(sender, "premiumpunishments help unban"); return; }

            if (unban(sender, args, true)) return;
            for (Player p : Bukkit.getOnlinePlayers()) if (p.hasPermission("premiumpunishments.staff-broadcasts")) p.sendMessage((messages().get("unban-broadcast-message") + ChatColor.WHITE + " [SILENT]").replaceAll("%admin%", sender.getName()).replaceAll("%player%", args[1]));
        } else { Bukkit.dispatchCommand(sender, "premiumpunishments help unban"); }
    }

    private boolean unban(CommandSender sender, String[] args, boolean silent) {
        boolean banned;

        if (silent) banned = checkBanned(args[1]); else banned = checkBanned(args[0]);

        if (!banned) {
            sender.sendMessage(prefix() + ChatColor.RED + "That player is not currently banned!");
            return true;
        }

        MinecraftPlayer mp;

        if (silent) mp = MinecraftPlayerRepository.getPlayerByUuid(Objects.requireNonNull(MojangAPI.getUuidByName(args[1]))); else mp = MinecraftPlayerRepository.getPlayerByUuid(Objects.requireNonNull(MojangAPI.getUuidByName(args[0])));

        if (silent) {
            database().execute("DELETE FROM " + PremiumPunishments.tablePrefix + "bans where uuid='" + UUID.fromString(MojangAPI.getUuidByName(args[1])));
            database().execute("DELETE FROM " + PremiumPunishments.tablePrefix + "players where uuid='" + UUID.fromString(MojangAPI.getUuidByName(args[1])) + "'");
            database().insertPlayer(mp.getUuid(), mp.getUsername(), false, mp.getBanexpirydate(), mp.getMuted(), mp.getMuteexpirydate(), mp.getWarns(), mp.getKicks());
            database().execute("DELETE FROM " + PremiumPunishments.tablePrefix + "banned_ips where uuid='" + UUID.fromString(MojangAPI.getUuidByName(args[1])) + "'");
        } else {
            database().execute("DELETE FROM " + PremiumPunishments.tablePrefix + "bans where uuid='" + UUID.fromString(MojangAPI.getUuidByName(args[0])) + "'");
            database().execute("DELETE FROM " + PremiumPunishments.tablePrefix + "players where uuid='" + UUID.fromString(MojangAPI.getUuidByName(args[0])) + "'");
            database().insertPlayer(mp.getUuid(), mp.getUsername(), false, mp.getBanexpirydate(), mp.getMuted(), mp.getMuteexpirydate(), mp.getWarns(), mp.getKicks());
            database().execute("DELETE FROM " + PremiumPunishments.tablePrefix + "banned_ips where uuid='" + UUID.fromString(MojangAPI.getUuidByName(args[0])) + "'");
        }

        if (sender instanceof Player) LogManager.addLog((Player) sender, "Unban", "None", args[0], "None");

        if (!silent) sender.sendMessage(prefix() + ChatColor.WHITE + "Successfully unbanned " + args[0] + "!"); else sender.sendMessage(prefix() + ChatColor.WHITE + "Successfully unbanned " + args[1] + "!");
        return false;
    }

    public boolean checkBanned(String name) {
        ResultSet set = database().query("SELECT username FROM " + PremiumPunishments.tablePrefix + "bans");

        boolean isBanned = false;

        try {
            while (set.next()) {
                if (set.getString("username").equalsIgnoreCase(name)) isBanned = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isBanned;
    }

}
