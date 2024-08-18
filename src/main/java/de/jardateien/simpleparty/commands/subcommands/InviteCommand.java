package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.party.Party;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InviteCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage("party_use_invite");
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage("party_invite_not_online");
            return;
        }

        if(request == player) {
            player.sendMessage("party_invite_own");
            return;
        }

        var manager = SimpleParty.getInstance().getPartyManager();
        var party = manager.getParty(player);
        if(party == null) {
            party = new Party(player);
            manager.joinParty(player, party);
        }

        if(!party.isLeader(player) && !party.isModerator(player)) {
            player.sendMessage("party_invite_not_permission");
            return;
        }

        var requestParty = manager.getParty(request);
        if(requestParty != null) {
            player.sendMessage("party_invite_already_in_party");
            return;
        }

        if(party.isBanned(request)) {
            player.sendMessage("");
            return;
        }

        if(party.hasRequested(request)) {
            player.sendMessage("party_invite_alreday_request");
            return;
        }

        party.getMembers().stream().filter(members -> members != player).toList().forEach(member -> member.sendMessage("party_invite_member"));
        request.sendMessage("party_invite_request");

        player.sendMessage("party_invite_sended");
        party.addRequests(request);
    }
}
