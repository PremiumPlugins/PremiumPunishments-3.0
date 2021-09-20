package com.exortions.premiumpunishments.commands.core.freeze;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Usage(usage = "<player>")
@Description(description = "Unfreeze a player. Unfreezing a player will allow the player to continue playing on the server like normal before they were frozen.")
public class UnfreezeCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
                return;
            }

            database().execute("DELETE FROM `" + database().getDatabase() + "`.`frozen_players` WHERE uuid='" + target.getUniqueId() + "';");

            sender.sendMessage(prefix() + "Successfully unfroze " + target.getName());
            PremiumPunishments.frozenPlayers.get(target.getUniqueId()).cancel();
            PremiumPunishments.frozenPlayers.remove(target.getUniqueId());
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help unfreeze");
    }
}
