package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.party.Party;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InviteCommand extends SubCommand {

    public InviteCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {

        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "use_invite"));
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_online", args[1]));
            return;
        }

        if(request == player) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "sender_is_same"));
            return;
        }

        var party = this.partyManager.getParty(player);
        if(party == null) {
            party = new Party(player);
            this.partyManager.setParty(player, party);
        }

        if(!party.isLeader(player)) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_leader"));
            return;
        }

        var requestParty = this.partyManager.getParty(request);
        if(requestParty != null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "already_in_other_party"));
            return;
        }

        if(party.hasRequest(request)) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_has_request", request.getName()));
            return;
        }

        party.getMembers().forEach(member -> member.sendMessage(this.languageManager.get(member, "player_send_request", request.getName())));
        request.sendMessage(Component.PARTY, this.languageManager.get(request, "player_sended_request", player.getName()));

        var accept = Component.event(this.languageManager.get(request, "click_accept").getText()).setCommand("/party accept " + player.getName()).showText("§a/party accept " + player.getName());
        var deny = Component.event(this.languageManager.get(request, "click_deny").getText()).setCommand("/party deny " + player.getName()).showText("§c/party deny " + player.getName());

        player.sendMessage(Component.PARTY, this.languageManager.get(request, "click_here_to"), Component.SPACE, accept.toText(), Component.SPACE, deny.toText());
        party.addRequests(request);
    }

}
