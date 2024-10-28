package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import de.jardateien.simpleparty.events.PartyPlayerJoinEvent;
import de.jardateien.simpleparty.party.Party;
import de.jardateien.simpleparty.utils.Component;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.LinkedList;
import java.util.List;

public class AcceptCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        List<String> list = new LinkedList<>();

        for (Party partys : SimpleParty.getInstance().getPartyManager().getValues()) {
            if(!partys.hasRequested(player)) continue;
            list.add(partys.getLeader().getName());
        }

        return args.length >= 3 ? List.of("") : list;
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, Component.text("Nutze: /party accept <Spieler>"));
            return;
        }

        var manager = SimpleParty.getInstance().getPartyManager();
        var party = manager.getParty(player);
        if(party != null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu hast selber eine Party!"));
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage(Component.PARTY, Component.text(String.format("§e%s §cisnt online!", args[1])));
            return;
        }

        var requestParty = manager.getParty(request);
        if(requestParty == null) {
            player.sendMessage(Component.PARTY, Component.text(String.format("§cDer Spieler §e%s §chat keine Party!", request.getName())));
            return;
        }

        if(!requestParty.hasRequested(player)) {
            player.sendMessage(Component.PARTY, Component.text("§cDu hast keine Party Anfrage bekommen!"));
            return;
        }

        if(requestParty.isBanned(player)) {
            requestParty.removeRequests(player);
            player.sendMessage(Component.PARTY, Component.text("§cDu wurdest von dieser Party Gebannt!"));
            return;
        }

        requestParty.removeRequests(player);
        manager.joinParty(player, requestParty);

        player.sendMessage(Component.PARTY, Component.text("Du hast die Party-Anfrage angenommen!"));
        var message = String.format("§a%s §7hat die Party betreten!", player.getName());
        requestParty.getMembers().forEach(member -> member.sendMessage(Component.PARTY, Component.text(message)));

        ProxyServer.getInstance().getPluginManager().callEvent(new PartyPlayerJoinEvent(requestParty, player));
    }

}
