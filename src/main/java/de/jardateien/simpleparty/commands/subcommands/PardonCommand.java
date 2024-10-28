package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import de.jardateien.simpleparty.events.PartyPlayerKickEvent;
import de.jardateien.simpleparty.events.PartyPlayerUnbanEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.LinkedList;
import java.util.List;

public class PardonCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        var party = SimpleParty.getInstance().getPartyManager().getParty(player);

        if(party == null) return List.of("");
        if(!party.isModerator(player) || !party.isLeader(player)) return List.of("");

        List<String> list = new LinkedList<>();
        party.getBanned().forEach(banned -> list.add(banned.getName()));
        return args.length >= 3 ? List.of("") : list;
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage("");
            return;
        }

        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) {
            player.sendMessage("");
            return;
        }

        if(!party.isLeader(player)) {
            player.sendMessage("");
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage("");
            return;
        }

        if(request == player) {
            player.sendMessage("");
            return;
        }

        if(!party.isBanned(request)) {
            player.sendMessage("");
            return;
        }

        party.removeBanned(request);
        player.sendMessage("");

        ProxyServer.getInstance().getPluginManager().callEvent(new PartyPlayerUnbanEvent(party, player, request));
    }
}
