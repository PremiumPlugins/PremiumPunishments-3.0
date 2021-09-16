package com.exortions.premiumpunishments.commands.core.warn;

import com.exortions.premiumpunishments.objects.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WarnCommand implements SubCommand {
    @Override
    public String name() {
        return "warn";
    }

    @Override
    public String permission() {
        return "premiumpunishments.warn";
    }

    @Override
    public String usage() {
        return "/premiumpunishments warn <player>";
    }

    @Override
    public String description() {
        return "Warn a player with a custom message. Warning a player will send them the custom message as a formal warning.";
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

    }
}
