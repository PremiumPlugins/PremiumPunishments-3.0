package com.exortions.premiumpunishments.commands.miscellaneous;

import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import com.exortions.premiumpunishments.objects.log.Log;
import com.exortions.premiumpunishments.objects.log.LogRepository;
import com.exortions.premiumpunishments.util.MojangAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;

@Usage(usage = "<player>")
@Description(description = "Clears the log of a player. This command should only be avaliable to the owners of the server.")
public class ClearlogCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            String uuid = MojangAPI.getUuidByName(args[0]);

            if (uuid == null) {
                sender.sendMessage(prefix() + ChatColor.RED + "Could not find that player!");
                return;
            }
            List<Log> logs = LogRepository.getLogsByUuid(uuid);

            if (Objects.requireNonNull(logs).isEmpty()) {
                sender.sendMessage(prefix() + ChatColor.RED + "That player does not have any logs yet!");
                return;
            }

            LogRepository.clearLogsByUuid(uuid);

            sender.sendMessage(prefix() + "Successfully cleared " + args[0] + "'s logs!");
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help clearlog");
    }
}
