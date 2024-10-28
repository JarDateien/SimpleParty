package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.party.Party;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.List;

public class PublicCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        return List.of("");
    }

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
