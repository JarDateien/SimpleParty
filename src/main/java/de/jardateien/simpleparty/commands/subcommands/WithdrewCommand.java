package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.List;

public class WithdrewCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        return List.of("");
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage("party_use_withdrew");
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage("party_withdrew_not_online");
            return;
        }

        if(request == player) {
            player.sendMessage("party_withdrew_own");
            return;
        }

        var manager = SimpleParty.getInstance().getPartyManager();
        var party = manager.getParty(player);
        if(party == null) {
            player.sendMessage("party_withdrew_not_party");
            return;
        }

        if(!party.isLeader(player) || !party.isModerator(player)) {
            player.sendMessage("party_withdrew_not_permission");
            return;
        }

        if(!party.hasRequested(request)) {
            player.sendMessage("party_invite_hased_request");
            return;
        }

        party.removeRequests(request);
        player.sendMessage("party_withdrew_removed");
    }

}
