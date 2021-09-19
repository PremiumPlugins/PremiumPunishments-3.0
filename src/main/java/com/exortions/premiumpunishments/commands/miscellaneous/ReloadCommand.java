package com.exortions.premiumpunishments.commands.miscellaneous;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Usage(usage = "reload")
@Description(description = "Reloads the Premium Punishments plugin.")
public class ReloadCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        final int[] ms = {0};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ms[0]++;
            }
        };
        timer.scheduleAtFixedRate(task, 1, 1);

        PremiumPunishments.getPlugin().reload();

        task.cancel();
        sender.sendMessage(PremiumPunishments.getPrefix() + "Successfully reloaded Premium Punishments in " + ms[0] + " ms.");
    }
}
