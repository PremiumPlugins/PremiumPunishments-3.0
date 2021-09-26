package com.exortions.premiumpunishments.listeners;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.settings.Settings;
import com.exortions.premiumpunishments.util.DatabaseHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FreezeListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (isFrozen(e.getPlayer()) && Settings.FREEZE_DISABLE_MOVEMENT) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (isFrozen(e.getPlayer()) && Settings.FREEZE_DISABLE_INTERACTIONS) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (isFrozen(e.getPlayer()) && Settings.FREEZE_DISABLE_INTERACTIONS) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (isFrozen(e.getPlayer()) && Settings.FREEZE_DISABLE_INTERACTIONS) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (isFrozen(e.getPlayer()) && Settings.FREEZE_DISABLE_CHATTING) e.setCancelled(true);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (isFrozen(e.getPlayer())) {
            for (String cmd : Settings.FREEZE_DISABLED_COMMANDS) if (e.getMessage().equals(cmd)) e.setCancelled(true);
        }
    }

    private boolean isFrozen(Player player) {
        try {
            DatabaseHandler db = PremiumPunishments.getPlugin().getDatabase();
            ResultSet set = db.query("SELECT * FROM " + PremiumPunishments.tablePrefix + "frozen_players");
            while (set.next()) {
                if (set.getString("uuid").equals(player.getUniqueId().toString())) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
