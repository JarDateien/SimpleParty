package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.party.Party;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PublicCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var manager = SimpleParty.getInstance().getPartyManager();
        var party = manager.getParty(player);
        if(party == null) {
            party = new Party(player);
            manager.joinParty(player, party);
        }

        if(!party.isLeader(player)) {
            player.sendMessage("Kein Leader");
            return;
        }

        if(party.isOpenParty()) {
            player.sendMessage("Ist schon Ofen");
            return;
        }

        manager.publicParty(party);
        player.sendMessage("Wurde eröffnet");
    }
}
