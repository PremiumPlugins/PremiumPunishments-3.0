package com.exortions.premiumpunishments.commands.core.freeze;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Usage(usage = "<player>")
@Description(description = "Freeze a player with a custom duration. Freezing a player prevents them from moving, breaking blocks, placing blocks, interacting with the world, and running chat commands. The player will remain frozen upon disconnecting and re-connecting to the server.")
public class FreezeCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
                return;
            }
            database().insertFrozenPlayer(target);
            sender.sendMessage(prefix() + "Successfully froze " + target.getName() + "!");
            target.sendMessage(messages().get("freeze-message"));
            if (!Settings.FREEZE_SPAM_MESSAGE) return;
            PremiumPunishments.frozenPlayers.put(target.getUniqueId(), new BukkitRunnable() {
                @Override
                public void run() {
                    target.sendMessage(messages().get("freeze-message"));
                }
            }.runTaskTimer(PremiumPunishments.getPlugin(), Settings.FREEZE_SPAM_MESSAGE_DELAY, 0L));
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help " + name());
    }

}
