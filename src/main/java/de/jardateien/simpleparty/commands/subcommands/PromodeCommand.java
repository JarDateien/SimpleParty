package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PromodeCommand extends SubCommand {
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

        if(!party.isInParty(request)) {
            player.sendMessage("");
            return;
        }

        if(party.isLeader(request)) {
            player.sendMessage("");
            return;
        }

        if(!party.isModerator(request)) {
            party.addModerator(request);
            player.sendMessage("");
            return;
        }

        party.setAction(player, () -> {
            party.setLeader(request);
            party.addModerator(player);
            party.removeModerator(request);
            party.getMembers().forEach(member -> member.sendMessage(""));
        });

        player.sendMessage("mach Confirm");
    }
}
