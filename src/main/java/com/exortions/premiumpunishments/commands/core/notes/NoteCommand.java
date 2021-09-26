package com.exortions.premiumpunishments.commands.core.notes;

import com.exortions.premiumpunishments.objects.command.Description;
import com.exortions.premiumpunishments.objects.command.RequiresPlayer;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.objects.command.Usage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiresPlayer
@Usage(usage = {"add <player> <name>", "remove <player> <name>", "list <player>"})
@Description(description = "Notes allow staff to make notes on different players. They can remove, add, and list notes they have on a player.")
public class NoteCommand implements SubCommand {

    @Override
    public void execute(Player player, String[] oldArgs) {
        if (oldArgs.length != 0) {
            List<String> ls = new ArrayList<>(Arrays.asList(oldArgs));
            ls.remove(0);
            String str = "";
            for (String s : ls) {
                str = str.concat(s + " ");
            }
            str = str.substring(0, str.length()-1);
            String[] args = str.split("\\s");
            switch (oldArgs[0]) {
                case "add":
                    new AddCommand().execute(player, args);
                    break;
                case "remove":
                    new RemoveCommand().execute(player, args);
                    break;
                case "list":
                    new ListCommand().execute(player, args);
                    break;
                default:
                    player.performCommand("premiumpunishments help note");
                    break;
            }
        } else player.performCommand("premiumpunishments help note");
    }
}
