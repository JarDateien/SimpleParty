package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class KickCommand extends SubCommand {
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

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage("");
            return;
        }

        if(!party.isLeader(player) || !party.isModerator(player)) {
            player.sendMessage("");
            return;
        }

        if(!party.isInParty(player)) {
            player.sendMessage("");
            return;
        }

        manager.leaveParty(request);
        request.sendMessage("");
    }
}
