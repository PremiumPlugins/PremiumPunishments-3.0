package com.exortions.premiumpunishments.commands.core.notes;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.util.MojangAPI;
import org.bukkit.entity.Player;

public class RemoveCommand implements SubCommand {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length >= 2) {
            String target = args[0];
            String name = "";
            int i = 0;
            for (String s : args) {
                if (i > 0) name = name.concat(s + " ");
                i++;
            }
            name = name.substring(0, name.length()-1);

            database().execute("DELETE FROM `" + database().getDatabase() + "`.`" + PremiumPunishments.tablePrefix + "notes` WHERE uuid='" + player.getUniqueId() + "' AND name='" + name + "' AND target='" + MojangAPI.getUuidByName(target) + "';");

            player.sendMessage(prefix() + "Successfully removed note '" + name + "' from " + target + "!");
        } else player.performCommand("premiumpunishments help note");
    }
}
