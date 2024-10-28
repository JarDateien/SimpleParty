package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.LinkedList;
import java.util.List;

public class DemoteCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) return List.of("");

        List<String> list = new LinkedList<>();
        party.getModerators().forEach(partyPlayer -> list.add(partyPlayer.getName()));
        return args.length >= 3 ? List.of("") : list;
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage("");
            return;
        }

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

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage("");
            return;
        }

        if(request == player) {
            player.sendMessage("");
            return;
        }

        if(!party.isInParty(request)) {
            player.sendMessage("");
            return;
        }

        if(party.isLeader(request)) {
            player.sendMessage("");
            return;
        }

        if(!party.isModerator(request)) {
            player.sendMessage("");
            return;
        }

        party.removeModerator(request);
        party.getModerators().forEach(mods -> mods.sendMessage(""));
    }
}
