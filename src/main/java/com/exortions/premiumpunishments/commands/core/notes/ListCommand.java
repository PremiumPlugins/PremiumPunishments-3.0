package com.exortions.premiumpunishments.commands.core.notes;

import com.exortions.premiumpunishments.PremiumPunishments;
import com.exortions.premiumpunishments.objects.command.SubCommand;
import com.exortions.premiumpunishments.util.MojangAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ListCommand implements SubCommand {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 1) {
            try {
                String uuid;
                try {
                    uuid = MojangAPI.getUuidByNameException(args[0]);
                } catch (IOException e) {
                    player.sendMessage(messages().get("unknown-player").replaceAll("%s", args[0]));
                    return;
                }
                UUID target = UUID.fromString(uuid);

                ResultSet set = database().query("SELECT * FROM " + PremiumPunishments.tablePrefix + "notes WHERE uuid='" + player.getUniqueId() + "' AND target='" + target + "'");

                List<String> notes = new ArrayList<>();

                boolean found = false;

                while (set.next()) {
                    found = true;
                    notes.add(set.getString("name"));
                }

                if (!found) {
                    player.sendMessage(prefix() + "You have no notes for " + args[0] + "!");
                    return;
                }

                player.sendMessage(prefix() + "Notes for " + args[0] + ":");
                for (String note : notes) player.sendMessage(ChatColor.RED + " - " + ChatColor.WHITE + note);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else player.performCommand("premiumpunishments help note");
    }
}
