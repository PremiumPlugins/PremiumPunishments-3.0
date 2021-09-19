package com.exortions.premiumpunishments.commands.miscellaneous;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

@Description(description = "Displays all commands and their usages.")
@Usage(usage = "help OR /premiumpunishments help <command>")
public class HelpCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        String prefix = PremiumPunishments.getPrefix();
        List<com.exortions.pluginutils.command.subcommand.SubCommand> subcommands = PremiumPunishments.getPlugin().getCommandHandler().getSubcommands();
        if (args.length == 0) {
            sender.sendMessage(prefix + ChatColor.WHITE + "Running " + ChatColor.RED + "PremiumPunishments v" + PremiumPunishments.getPlugin().getPluginVersion() + ChatColor.WHITE + ".");

            for (com.exortions.pluginutils.command.subcommand.SubCommand subcommand : subcommands) {
                String[] usages = subcommand.usage().split(" OR ");
                for (String usage : usages) sender.sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + usage);
            }
        } else {
            sender.sendMessage(prefix + ChatColor.WHITE + "Running " + ChatColor.RED + "PremiumPunishments v" + PremiumPunishments.getPlugin().getPluginVersion() + ChatColor.WHITE + ".");

            for (com.exortions.pluginutils.command.subcommand.SubCommand subcommand : subcommands) {
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
