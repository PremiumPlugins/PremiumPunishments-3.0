package com.exortions.premiumpunishments.objects.command;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.util.DatabaseHandler;

import java.util.HashMap;

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
    default String permission() {
        return "premiumpunishments." + name();
    }

    @Override
    default String usage() {
        return "/premiumpunishments ";
    }
}
