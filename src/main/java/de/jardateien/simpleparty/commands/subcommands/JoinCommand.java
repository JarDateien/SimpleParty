package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class JoinCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage("party_use_join_discritions");
            return;
        }

        var manager = SimpleParty.getInstance().getPartyManager();

        var party = manager.getParty(player);
        if(party != null) {
            player.sendMessage("");
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage("");
            return;
        }

        var requestParty = manager.getParty(request);
        if(requestParty == null) {
            player.sendMessage("");
            return;
        }

        if(!requestParty.isOpenParty()) {
            player.sendMessage("");
            return;
        }

        if(requestParty.isBanned(player)) {
            player.sendMessage("");
            return;
        }

        manager.joinParty(player, requestParty);
    }

}
