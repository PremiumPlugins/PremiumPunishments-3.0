package com.exortions.premiumpunishments.commands.miscellaneous;

import com.exortions.pluginutils.command.subcommand.SubCommand;
import com.exortions.premiumpunishments.PremiumPunishments;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpCommand implements SubCommand {
    @Override
    public String name() {
        return "help";
    }

    @Override
    public String permission() {
        return "premiumpunishments.help";
    }

    @Override
    public String usage() {
        return "/premiumpunishments help OR /premiumpunishments help <command>";
    }

    @Override
    public String description() {
        return "Displays all commands and their usages.";
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
        String prefix = PremiumPunishments.getPrefix();
        List<SubCommand> subcommands = PremiumPunishments.getPlugin().getCommandHandler().getSubcommands();
        if (args.length == 0) {
            sender.sendMessage(prefix + ChatColor.WHITE + "Running " + ChatColor.RED + "PremiumPunishments v" + PremiumPunishments.getPlugin().getPluginVersion() + ChatColor.WHITE + ".");

            for (SubCommand subcommand : subcommands) {
                String[] usages = subcommand.usage().split(" OR ");
                for (String usage : usages) sender.sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + usage);
            }
        } else {
            sender.sendMessage(prefix + ChatColor.WHITE + "Running " + ChatColor.RED + "PremiumPunishments v" + PremiumPunishments.getPlugin().getPluginVersion() + ChatColor.WHITE + ".");

            for (SubCommand subcommand : subcommands) {
                if (args[0].equals(subcommand.name())) {
                    sender.sendMessage(prefix + ChatColor.RED + StringUtils.capitalize(subcommand.name()) + ChatColor.WHITE + " (" + subcommand.permission() + "):");
                    String[] usages = subcommand.usage().split(" OR ");
                    sender.sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + subcommand.description());
                    for (String usage : usages) sender.sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + usage);
                }
            }
        }
    }
}
