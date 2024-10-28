package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.LinkedList;
import java.util.List;

public class JoinCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        var manager = SimpleParty.getInstance().getPartyManager();
        if(manager.getParty(player) != null) return List.of("");

        List<String> list = new LinkedList<>();
        manager.getPublic().forEach(party -> list.add(party.getLeader().getName()));
        return args.length >= 3 ? List.of("") : list;
    }

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
