package com.exortions.premiumpunishments.listeners;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.enums.MuteType;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayer;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.objects.mute.Mute;
import com.exortions.premiumpunishments.objects.mute.MuteRepository;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        MinecraftPlayer mp = Objects.requireNonNull(MinecraftPlayerRepository.getPlayerByUuid(e.getPlayer().getUniqueId()));
        if (mp.getMuted()) {
            Mute mute = Objects.requireNonNull(MuteRepository.getMuteByUuid(e.getPlayer().getUniqueId()));
            if (mute.getType() == MuteType.mute) {
                if (mp.getMuteexpirydate().getTime() < System.currentTimeMillis()) {
                    mp.setMuted(false);
                    PremiumPunishments.getPlugin().getDatabase().execute("DELETE FROM mutes WHERE uuid='" + e.getPlayer().getUniqueId() + "'");
                    return;
                }
                e.getPlayer().sendMessage(Placeholders.setMutePlaceholders(PremiumPunishments.getPlugin().getMessages().get("mute-message"), mute));
                e.setCancelled(true);
            } else if (mute.getType() == MuteType.perm) {
                e.getPlayer().sendMessage(Placeholders.setMutePlaceholders(PremiumPunishments.getPlugin().getMessages().get("perm-mute-message"), mute));
                e.setCancelled(true);
            }
        }
    }

}
