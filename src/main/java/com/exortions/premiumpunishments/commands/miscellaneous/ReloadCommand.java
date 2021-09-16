package com.exortions.premiumpunishments.commands.miscellaneous;

import com.exortions.pluginutils.command.subcommand.SubCommand;
import com.exortions.premiumpunishments.PremiumPunishments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReloadCommand implements SubCommand {
    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String permission() {
        return "premiumpunishments.reload";
    }

    @Override
    public String usage() {
        return "/premiumpunishments reload";
    }

    @Override
    public String description() {
        return "Reloads the Premium Punishments plugin.";
    }

    @Override
    public List<String> tabcompletion() {
        return null;
    }

    @Override
    public boolean requiresPlayer() {
        return false;
    }

    @Override
    public void execute(Player player, String[] args) {

    }

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

        PremiumPunishments.getPlugin().reload();

        task.cancel();
        sender.sendMessage(PremiumPunishments.getPrefix() + "Successfully reloaded Premium Punishments in " + ms[0] + " ms.");
    }
}
