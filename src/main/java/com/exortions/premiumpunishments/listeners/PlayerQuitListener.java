package com.exortions.premiumpunishments.listeners;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "players WHERE uuid='" + player.getUniqueId() + "'");

        MinecraftPlayer mp = Objects.requireNonNull(MinecraftPlayerRepository.getPlayerByUuid(player.getUniqueId()));

        try {
            if (set.next()) if (mp.getBanned()) e.setQuitMessage(null);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
