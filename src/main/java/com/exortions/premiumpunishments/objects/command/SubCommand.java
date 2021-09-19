package com.exortions.premiumpunishments.objects.command;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.util.DatabaseHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public interface SubCommand extends com.exortions.pluginutils.command.subcommand.SubCommand {

    default String prefix() {
        return PremiumPunishments.getPrefix();
    }

    default HashMap<String, String> messages() {
        return PremiumPunishments.getPlugin().getMessages();
    }

    default DatabaseHandler database() {
        return PremiumPunishments.getPlugin().getDatabase();
    }

    @Override
    default String name() {
        return getClass().getName().split("\\.")[getClass().getName().split("\\.").length-1].toLowerCase().replaceAll("command", "");
    }

    @Override
    default String permission() {
        if (!getClass().isAnnotationPresent(Permission.class)) return "premiumpunishments." + name();
        return "premiumpunishments." + getClass().getDeclaredAnnotation(Permission.class).permission();
    }

    @Override
    default String usage() {
        if (!getClass().isAnnotationPresent(Usage.class)) return "/premiumpunishments " + name();
        String[] usages = getClass().getDeclaredAnnotation(Usage.class).usage();
        String s = "";
        for (String usage : usages) {
            s = "/premiumpunishments ".concat(name()).concat(" " + usage + " OR ");
        }
        s = s.substring(0, s.length()-4);
        return s;
    }

    @Override
    default String description() {
        if (!getClass().isAnnotationPresent(Description.class)) return "";
        return getClass().getDeclaredAnnotation(Description.class).description();
    }

    @Override
    default List<String> tabcompletion() {
        if (!getClass().isAnnotationPresent(TabCompletion.class)) return null;
        List<String> ls = new ArrayList<>();
        Collections.addAll(ls, getClass().getDeclaredAnnotation(TabCompletion.class).tabCompletions());
        return ls;
    }

    @Override
    default boolean requiresPlayer() {
        if (!getClass().isAnnotationPresent(RequiresPlayer.class)) return false;
        return getClass().getDeclaredAnnotation(RequiresPlayer.class).requiresPlayer();
    }

    @Override
    default void execute(Player player, String[] args) { }

    @Override
    default void execute(CommandSender sender, String[] args) { }
}
