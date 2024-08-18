package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrivateCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var manager = SimpleParty.getInstance().getPartyManager();
        var party = manager.getParty(player);
        if(party == null) {
            player.sendMessage("");
            return;
        }

        if(!party.isLeader(player)) {
            player.sendMessage("");
            return;
        }

        if(!party.isOpenParty()) {
            player.sendMessage("");
            return;
        }

        manager.privateParty(party);
        player.sendMessage("");
    }
}
