package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AcceptCommand extends SubCommand {
    public AcceptCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "use_accept_help"));
            return;
        }

        var party = this.partyManager.getParty(player);
        if(party != null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_already_in_party"));
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_online", args[1]));
            return;
        }

        var requestParty = this.partyManager.getParty(request);
        if(requestParty == null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_dont_have_party"));
            return;
        }

        if(!requestParty.hasRequest(player)) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_dont_have_request"));
            return;
        }

        requestParty.removeRequests(player);
        this.partyManager.setParty(player, requestParty);
    }
}
