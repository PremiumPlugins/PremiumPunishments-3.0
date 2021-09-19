package com.exortions.premiumpunishments.commands.core.warn;

import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import org.bukkit.command.CommandSender;

@Usage(usage = "<player>")
@Description(description = "Warn a player with a custom message. Warning a player will send them the custom message as a formal warning.")
public class WarnCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

    }

}
