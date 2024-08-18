package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class JumpCommand extends SubCommand {

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
