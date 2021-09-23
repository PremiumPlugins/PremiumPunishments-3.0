package com.exortions.premiumpunishments.commands.core.warn;

import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.minecraftplayer.MinecraftPlayerRepository;
import com.exortions.premiumpunishments.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@Usage(usage = "<player> <reason>")
@Description(description = "Warn a player with a custom message. Warning a player will send them the custom message as a formal warning.")
public class WarnCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target != null) {

                String reason = "";
                int i = 0;
                for (String s : args) {
                    if (i > 1) reason = reason.concat(s + " ");
                    i++;
                }
                reason = reason.substring(0, reason.length()-1);

                Objects.requireNonNull(MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId())).setWarns(Objects.requireNonNull(MinecraftPlayerRepository.getPlayerByUuid(target.getUniqueId())).getWarns());

                target.sendMessage(Placeholders.setWarnPlaceholders(messages().get("warn-message"), reason, sender));

                sender.sendMessage(prefix() + "Successfully warned " + target.getName() + " for " + reason + "!");
            } else sender.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help warn");
    }

}
