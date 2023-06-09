package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PromodeCommand extends SubCommand {
    public PromodeCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, Component.text("§7/party promode <Spieler>"));
            return;
        }

        var party = this.partyManager.getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_in_party"));
            return;
        }

        if(!party.isLeader(player)) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_leader"));
            return;
        }

        if(party.isLeader(player)) {
            player.sendMessage(Component.PARTY, Component.text("§cDu bist bereits der Party Leiter!"));
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_online" , args[1]));
            return;
        }

        var requestParty = this.partyManager.getParty(request);
        if(requestParty == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDer Spieler ist in keiner Party"));
            return;
        }

        if(!party.isInParty(request)) {
            player.sendMessage(Component.PARTY, Component.text("§cDer Spieler ist nicht in deiner Party!"));
            return;
        }

        if(!party.isModerator(request)) {
            party.addModerator(request);
            return;
        }

        party.addModerator(player);
        party.setLeader(request);
        party.sendMembers("§a" + request.getName() + " §7wurde zum Party Leitung ernannt!");
    }
}
