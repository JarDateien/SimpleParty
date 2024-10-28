package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import de.jardateien.simpleparty.events.PartyPlayerBanEvent;
import de.jardateien.simpleparty.events.PartyPlayerUnbanEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.LinkedList;
import java.util.List;

public class PunishCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) return List.of("");

        List<String> list = new LinkedList<>();
        party.getMembers().forEach(partyPlayer -> list.add(partyPlayer.getName()));
        return args.length >= 3 ? List.of("") : list;
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage("");
            return;
        }

        var manager = SimpleParty.getInstance().getPartyManager();
        var party = manager.getParty(player);
        if(party == null) {
            player.sendMessage("Keine Party");
            return;
        }

        if(party.isLeader(player)) {
            player.sendMessage("Leader");
        }

        if(party.isModerator(player)) {
            player.sendMessage("Moderator");
        }

        if(!party.isLeader(player) && !party.isModerator(player)) {
            player.sendMessage("Keine Rechte");
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage("Nicht Online");
            return;
        }

        if(request == player) {
            player.sendMessage("Bist selber");
            return;
        }

        if(party.isLeader(request)) {
            player.sendMessage("Ist Leader");
            return;
        }

        if(party.isBanned(request)) {
            player.sendMessage("Ist gebannt");
            return;
        }

        //if(party.isLeader(player) && party.isModerator(request)) {
        //   party.setAction(() -> {
        //      manager.leaveParty(request);
        //      party.addBanned(request);
        //      player.sendMessage("");
        //      request.sendMessage("");
        //  });
        //
        //  player.sendMessage("mach confirm");
        //  return;
        //}

        if(party.isInParty(request)) { manager.leaveParty(request); }
        if(party.hasRequested(request)) { party.removeRequests(request); }

        party.addBanned(request);

        player.sendMessage("Hast");
        request.sendMessage("Gebannt");

        ProxyServer.getInstance().getPluginManager().callEvent(new PartyPlayerBanEvent(party, player, request));
    }
}
