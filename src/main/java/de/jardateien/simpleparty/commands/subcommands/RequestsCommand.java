package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.party.Party;
import de.jardateien.simpleparty.utils.Component;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.LinkedList;

public class RequestsCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var list = new LinkedList<Party>();
        var values = SimpleParty.getInstance().getPartyManager().getValues();
        for (Party party : values) {
            if(!party.hasRequested(player)) continue;
            if(list.contains(party)) continue;
            list.add(party);
        }

        if(list.isEmpty()) {
            player.sendMessage("Nix");
            return;
        }

        player.sendMessage(Component.PARTY, Component.text("§eParty-Einladungsanfragen §7(" + list.size() + ")"));
        list.forEach(party -> player.sendMessage(Component.PARTY, Component.text("§7- " + party.getLeader().getName())));
    }

}
