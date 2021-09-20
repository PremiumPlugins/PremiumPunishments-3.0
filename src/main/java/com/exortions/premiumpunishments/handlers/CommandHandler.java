package com.exortions.premiumpunishments.handlers;

import com.exortions.pluginutils.command.subcommand.SubCommand;
import com.exortions.pluginutils.command.subcommand.SubCommandHandler;
import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.commands.core.ban.BanCommand;
import com.exortions.premiumpunishments.commands.core.ban.UnbanCommand;
import com.exortions.premiumpunishments.commands.core.freeze.FreezeCommand;
import com.exortions.premiumpunishments.commands.core.freeze.UnfreezeCommand;
import com.exortions.premiumpunishments.commands.core.kick.KickCommand;
import com.exortions.premiumpunishments.commands.core.mute.MuteCommand;
import com.exortions.premiumpunishments.commands.core.mute.UnmuteCommand;
import com.exortions.premiumpunishments.commands.core.notes.NoteCommand;
import com.exortions.premiumpunishments.commands.core.warn.WarnCommand;
import com.exortions.premiumpunishments.commands.miscellaneous.HelpCommand;
import com.exortions.premiumpunishments.commands.miscellaneous.InfoCommand;
import com.exortions.premiumpunishments.commands.miscellaneous.ReloadCommand;
import org.bukkit.ChatColor;

import java.util.List;

public class CommandHandler extends SubCommandHandler {

    public CommandHandler() {
        super(null, null, null, null);
        String prefix = PremiumPunishments.getPrefix();
        setOnlyPlayers(() -> getSender().sendMessage(PremiumPunishments.getPlugin().getMessages().get("only-players")));
        setNoArguments(() -> {
            String message;
            message = prefix + "Running " + ChatColor.RED + "PremiumPunishments v" + PremiumPunishments.getPlugin().getPluginVersion() + ChatColor.WHITE + ".\n";
            boolean permission = false;
            for (SubCommand subcommand : new CommandHandler().getSubcommands()) if (getSender().hasPermission(subcommand.permission())) permission = true;
            if (permission) message = message.concat(ChatColor.RED + " > " + ChatColor.WHITE + "Type /premiumpunishments help for more info."); else message = message.concat(ChatColor.RED + " > " + ChatColor.WHITE + "You do not have permission to use any sub-commands.");
            getSender().sendMessage(message);
        });
        setNoPermission(() -> getSender().sendMessage(PremiumPunishments.getPlugin().getMessages().get("no-permission")));
        setSubComandNotFound(() -> getSender().sendMessage(PremiumPunishments.getPlugin().getMessages().get("unknown-command")));

        List<SubCommand> subCommands = getSubcommands();

        subCommands.add(new UnfreezeCommand());
        subCommands.add(new FreezeCommand());
        subCommands.add(new ReloadCommand());
        subCommands.add(new UnmuteCommand());
        subCommands.add(new UnbanCommand());
        subCommands.add(new NoteCommand());
        subCommands.add(new KickCommand());
        subCommands.add(new WarnCommand());
        subCommands.add(new MuteCommand());
        subCommands.add(new HelpCommand());
        subCommands.add(new InfoCommand());
        subCommands.add(new BanCommand());

        setSubcommands(subCommands);
    }

}
