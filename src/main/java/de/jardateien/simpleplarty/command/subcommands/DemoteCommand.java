package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DemoteCommand extends SubCommand {
    public DemoteCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "use_demote_help"));
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
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_cant_remove_leader"));
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

        if(!party.isInParty(request)) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_is_in_my_party"));
            return;
        }

        if(!party.isModerator(player)) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_is_not_moderator"));
            return;
        }

        party.removeModerator(player);
        party.getMembers().forEach(members -> members.sendMessage(this.languageManager.get(members, "player_is_not_longer_moderator", player.getName())));
    }
}
