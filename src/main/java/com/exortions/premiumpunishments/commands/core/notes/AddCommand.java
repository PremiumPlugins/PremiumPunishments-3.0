package com.exortions.premiumpunishments.commands.core.notes;

import com.exortions.premiumpunishments.objects.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AddCommand implements SubCommand {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length >= 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
                return;
            }
            String name = "";
            int i = 0;
            for (String s : args) {
                if (i > 0) name = name.concat(s + " ");
                i++;
            }
            name = name.substring(0, name.length()-1);

            database().insertNote(player.getUniqueId(), name, target.getUniqueId().toString());

            player.sendMessage(prefix() + "Successfully added note to " + target.getName() + "!");
        } else player.performCommand("premiumpunishments help note");
    }

}
