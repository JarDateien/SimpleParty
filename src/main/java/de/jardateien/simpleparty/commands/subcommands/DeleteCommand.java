package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.List;

public class DeleteCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        return List.of("");
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var manager = SimpleParty.getInstance().getPartyManager();
        if(args.length <= 1 || !player.hasPermission("party.delete.other")) {
            var party = manager.getParty(player);
            if(party == null) {
                player.sendMessage("");
                return;
            }

            if(!party.isLeader(player)) {
                player.sendMessage("");
                return;
            }

            party.setAction(player, () -> {
                manager.delete(party);
                player.sendMessage("");
            });

            //manager.delete(party);
            player.sendMessage("Mach confirm");
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

        manager.delete(requestParty);
        player.sendMessage("");
    }
}
