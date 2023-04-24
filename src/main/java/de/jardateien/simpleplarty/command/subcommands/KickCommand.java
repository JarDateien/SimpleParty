package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class KickCommand extends SubCommand {

    public KickCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, Component.text("§7/party kick <Spieler>"));
            return;
        }

        var party = this.partyManager.getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu bist in keiner Party!"));
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage(Component.PARTY, Component.text("§e" + args[1] + " §cist nicht online!"));
            return;
        }

        if(!party.isLeader(player)) {
            player.sendMessage(Component.PARTY, Component.text("§cDu kannst keine Spieler aus dieser Party kicken!"));
            return;
        }

        if(!party.isInParty(request)) {
            player.sendMessage(Component.PARTY, Component.text("§cDer Spieler §a" + args[1] + " §cist nicht in deiner Party!"));
            return;
        }

        request.sendMessage(Component.PARTY, Component.text("§cDu wurdest aus der Party geworfen!"));
        this.partyManager.removeParty(request);
    }
}
