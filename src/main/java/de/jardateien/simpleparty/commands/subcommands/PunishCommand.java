package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PunishCommand extends SubCommand {

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
    }
}
