package com.exortions.premiumpunishments.handlers;

import com.exortions.pluginutils.command.subcommand.SubCommand;
import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.ban.Ban;
import com.exortions.premiumpunishments.objects.ban.BanRepository;
import net.minecraft.server.v1_16_R3.AttributeModifier;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.geom.RectangularShape;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TabCompleteHandler implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String a, @NotNull String[] args) {
        List<SubCommand> subcommands = new CommandHandler().getSubcommands();
        List<String> tab = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("premiumpunishments") || cmd.getName().equalsIgnoreCase("pp")) {
            switch (args.length) {
                case 1:
                    boolean perms = false;
                    for (SubCommand subcommand : new CommandHandler().getSubcommands())
                        if (sender.hasPermission(subcommand.permission())) perms = true;
                    if (perms) for (SubCommand subcommand : subcommands) tab.add(subcommand.name());
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
                            if (args[1].isEmpty())
                                for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                            else for (Player player : Bukkit.getOnlinePlayers())
                                if (player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                                    tab.add(player.getName());
                            tab.add("-s");
                            break;
                        case "note":
                            tab.add("add");
                            tab.add("list");
                            tab.add("remove");
                            break;
                        default:
                            for (SubCommand subcommand : subcommands)
                                if (subcommand.tabcompletion() != null)
                                    if (subcommand.name().equals(args[0])) tab.addAll(subcommand.tabcompletion());
                                    else
                                        for (Player player : Bukkit.getOnlinePlayers()) {
                                            tab.add(player.getName());
                                        }
                            break;
                    }
                    break;
                case 3:
                    if (args[0].equals("ban") || args[0].equals("mute")) {
                        if (args[1].equals("-s")) {
                            if (args[2].isEmpty())
                                for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                            else for (Player player : Bukkit.getOnlinePlayers())
                                if (player.getName().toLowerCase().startsWith(args[2].toLowerCase()))
                                    tab.add(player.getName());
                            break;
                        }
                        if (args[2].isEmpty()) break;
                        if (args[2].contains("s") || args[2].contains("m") || args[2].contains("h") || args[2].contains("d") || args[2].contains("y"))
                            break;
                        tab.add(args[2] + "s");
                        tab.add(args[2] + "m");
                        tab.add(args[2] + "h");
                        tab.add(args[2] + "d");
                        tab.add(args[2] + "y");
                        break;
                    } else if (args[0].equals("kick") && args[1].equals("-s")) {
                        if (args[2].isEmpty())
                            for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                        else for (Player player : Bukkit.getOnlinePlayers())
                            if (player.getName().toLowerCase().startsWith(args[2].toLowerCase()))
                                tab.add(player.getName());
                        break;
                    } else if (args[0].equals("note")) {
                        if (args[1].equals("add") || args[1].equals("remove") || args[1].equals("list")) {
                            if (args[2].isEmpty())
                                for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                            else for (Player player : Bukkit.getOnlinePlayers())
                                if (player.getName().toLowerCase().startsWith(args[2].toLowerCase()))
                                    tab.add(player.getName());
                            break;
                        }
                    }
                    break;
                case 4:
                    if (args[0].equals("ban") || args[0].equals("mute")) {
                        if (!args[1].equals("-s")) break;
                        if (args[3].isEmpty()) break;
                        if (args[3].contains("s") || args[3].contains("m") || args[3].contains("h") || args[3].contains("d") || args[3].contains("y"))
                            break;
                        tab.add(args[3] + "s");
                        tab.add(args[3] + "m");
                        tab.add(args[3] + "h");
                        tab.add(args[3] + "d");
                        tab.add(args[3] + "y");
                    } else if (args[0].equals("note")) {
                        if (args[1].equals("remove")) {
                            try {
                                Player player = Bukkit.getPlayer(args[3]);
                                if (player == null) break;
                                ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "notes WHERE uuid='" + ((Player) sender).getUniqueId() + "' AND target='" + player.getUniqueId() + "'");

                                while (set.next()) tab.add(set.getString("name"));
                                break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else if (args[1].equals("add")) {
                            if (args[3].isEmpty()) tab.add("<name>");
                            else tab.remove("<name>");
                        }
                    }
                    break;
            }
        } else {
            switch (args.length) {
                case 1:
                    switch (cmd.getName()) {
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
                            if (args[0].isEmpty())
                                for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                            else for (Player player : Bukkit.getOnlinePlayers())
                                if (player.getName().toLowerCase().startsWith(cmd.getName().toLowerCase()))
                                    tab.add(player.getName());
                            tab.add("-s");
                            break;
                        case "note":
                            tab.add("add");
                            tab.add("list");
                            tab.add("remove");
                            break;
                        default:
                            for (SubCommand subcommand : subcommands)
                                if (subcommand.tabcompletion() != null)
                                    if (subcommand.name().equals(cmd.getName())) tab.addAll(subcommand.tabcompletion());
                                    else
                                        for (Player player : Bukkit.getOnlinePlayers()) {
                                            tab.add(player.getName());
                                        }
                            break;
                    }
                    break;
                case 2:
                    if (cmd.getName().equals("ban") || cmd.getName().equals("mute")) {
                        if (args[0].equals("-s")) {
                            if (args[1].isEmpty())
                                for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                            else for (Player player : Bukkit.getOnlinePlayers())
                                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                                    tab.add(player.getName());
                            break;
                        }
                        if (args[1].isEmpty()) break;
                        if (args[1].contains("s") || args[1].contains("m") || args[1].contains("h") || args[1].contains("d") || args[1].contains("y"))
                            break;
                        tab.add(args[1] + "s");
                        tab.add(args[1] + "m");
                        tab.add(args[1] + "h");
                        tab.add(args[1] + "d");
                        tab.add(args[1] + "y");
                        break;
                    } else if (cmd.getName().equals("kick") && args[0].equals("-s")) {
                        if (args[1].isEmpty())
                            for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                        else for (Player player : Bukkit.getOnlinePlayers())
                            if (player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                                tab.add(player.getName());
                        break;
                    } else if (cmd.getName().equals("note")) {
                        if (args[0].equals("add") || args[0].equals("remove") || args[0].equals("list")) {
                            if (args[1].isEmpty())
                                for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                            else for (Player player : Bukkit.getOnlinePlayers())
                                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                                    tab.add(player.getName());
                            break;
                        }
                    }
                    break;
                case 4:
                    if (cmd.getName().equals("ban") || cmd.getName().equals("mute")) {
                        if (!args[0].equals("-s")) break;
                        if (args[2].isEmpty()) break;
                        if (args[2].contains("s") || args[2].contains("m") || args[2].contains("h") || args[2].contains("d") || args[2].contains("y"))
                            break;
                        tab.add(args[2] + "s");
                        tab.add(args[2] + "m");
                        tab.add(args[2] + "h");
                        tab.add(args[2] + "d");
                        tab.add(args[2] + "y");
                    } else if (cmd.getName().equals("note")) {
                        if (args[0].equals("remove")) {
                            try {
                                Player player = Bukkit.getPlayer(args[2]);
                                if (player == null) break;
                                ResultSet set = PremiumPunishments.getPlugin().getDatabase().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "notes WHERE uuid='" + ((Player) sender).getUniqueId() + "' AND target='" + player.getUniqueId() + "'");

                                while (set.next()) tab.add(set.getString("name"));
                                break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else if (args[0].equals("add")) {
                            if (args[2].isEmpty()) tab.add("<name>");
                            else tab.remove("<name>");
                        }
                    }
                    break;
            }
        }
        return tab;
    }

}
