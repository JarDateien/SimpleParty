package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Arrays;
import java.util.List;

public class JumpCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        return List.of("");
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) {
            player.sendMessage("");
            return;
        }

        if(party.isLeader(player)) {
            player.sendMessage("");
            return;
        }

        var leaderInfo = party.getLeader().getServer().getInfo();
        if(player.getServer().getInfo() == leaderInfo) {
            player.sendMessage("");
            return;
        }

        player.connect(leaderInfo);
    }
}
