package com.exortions.premiumpunishments.handlers;

import com.exortions.pluginutils.command.subcommand.SubCommand;
import com.exortions.premiumpunishments.objects.ban.Ban;
import com.exortions.premiumpunishments.objects.ban.BanRepository;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TabCompleteHandler implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String a, @NotNull String[] args) {
        List<SubCommand> subcommands = new CommandHandler().getSubcommands();
        List<String> tab = new ArrayList<>();
        switch (args.length) {
            case 1:
                for (SubCommand subcommand : subcommands) tab.add(subcommand.name());
                break;
            case 2:
                switch (args[0]) {
                    case "help":
                        for (SubCommand subcommand : subcommands) tab.add(subcommand.name());
                        break;
                    case "reload":
                    case "info":
                        break;
                    case "unban":
                        if (BanRepository.getAllBans().isEmpty()) {
                            tab.add("No one is currently banned!");
                            break;
                        }
                        for (Ban ban : BanRepository.getAllBans()) {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(ban.getUuid()));
                            tab.add(target.getName());
                        }
                        break;
                    case "ban":
                    case "mute":
                    case "kick":
                        for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                        break;
                    default:
                        for (SubCommand subcommand : subcommands) if (subcommand.tabcompletion() != null) if (subcommand.name().equals(args[0])) tab.addAll(subcommand.tabcompletion()); else tab.add("");
                        break;
                }
        }
        return tab;
    }
}
