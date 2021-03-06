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
@Description(description = "View all logs for a staff member. This will show what commands they've been using.")
public class LogCommand implements SubCommand {

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

            String message = "";

            message = message.concat(prefix() + args[0] + "'s logs:\n" + ChatColor.RED + " > " + ChatColor.WHITE + "Format: Type, Player, Date\n");

            int ln = 0;

            for (Log log : logs) {
                ln++;
                message = message.concat(ChatColor.RED + "  " + ln + ". " + ChatColor.DARK_RED + log.getType() + " " + ChatColor.WHITE + log.getTarget() + " " + log.getDate() + "\n");
            }

            message = message.concat(prefix() + ChatColor.WHITE + "END LOG");

            sender.sendMessage(message);
        } else Bukkit.dispatchCommand(sender, "premiumpunishments help log");
    }
}
