package com.exortions.premiumpunishments.listeners;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.BanType;
import com.exortions.premiumpunishments.objects.ban.Ban;
import com.exortions.premiumpunishments.objects.ban.BanRepository;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("ConstantConditions")
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM players WHERE uuid='" + player.getUniqueId() + "'");

        MinecraftPlayer p = MinecraftPlayerRepository.getPlayerByUuid(player.getUniqueId());

        ResultSet ips = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM banned_ips");

        try {
            while (ips.next()) {
                if (ips.getString("ip").equals(player.getAddress().getHostName())) {
                    if (!p.getBanned()) {
                        Ban ban = BanRepository.getBanByUuid(ips.getString("uuid"));
                        System.out.println("premiumpunishments ban " + player.getName() + " -1 " + ban.getReason());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "premiumpunishments ban " + player.getName() + " -1 " + ban.getReason());
                    }
                }
            }
            if (!set.next()) {
                PremiumPunishments.getPlugin().getDatabase().insertNewPlayer(player.getUniqueId(), player.getName());
            } else {
                if (p.getBanned()) {
                    Ban ban = BanRepository.getBanByUuid(player.getUniqueId());
                    if (ban.getType() == BanType.ban) {
                        if (p.getBanexpirydate().getTime() < System.currentTimeMillis()) {
                            p.setBanned(false);
                            PremiumPunishments.getPlugin().getDatabase().execute("DELETE FROM bans WHERE uuid='" + player.getUniqueId() + "'");
                            return;
                        }
                        player.kickPlayer(Placeholders.setBanPlaceholders(PremiumPunishments.getPlugin().getMessages().get("ban-message"), ban));
                    } else if (ban.getType() == BanType.perm) {
                        player.kickPlayer(Placeholders.setBanPlaceholders(PremiumPunishments.getPlugin().getMessages().get("perm-ban-message"), ban));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
