package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.party.PartyManager;
import de.jardateien.simpleplarty.utils.Component;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AcceptCommand extends SubCommand {
    public AcceptCommand(PartyManager partyManager) { super(partyManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, Component.text("§7/party accept <Spieler>"));
            return;
        }

        var party = this.partyManager.getParty(player);
        if(party != null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu bist bereits in einer Party!"));
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage(Component.PARTY, Component.text("§e" + args[1] + " §cist nicht online!"));
            return;
        }

        var requestParty = this.partyManager.getParty(request);
        if(requestParty == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDieser Spieler hat keine Party"));
            return;
        }

        if(!requestParty.hasRequest(player)) {
            player.sendMessage(Component.PARTY, Component.text("§7[§5Party§7] §cDu wurdest nicht in diese Party eingeladen!"));
            return;
        }

        requestParty.removeRequests(player);
        this.partyManager.setParty(player, requestParty);
    }
}
