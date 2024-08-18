package de.jardateien.simpleparty.listener;

import de.jardateien.simpleparty.SimpleParty;
import de.jardateien.simpleparty.utils.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyListener implements Listener {

    @EventHandler
    public void onQuit(PlayerDisconnectEvent disconnectEvent) {

    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent switchEvent) {
        var player = switchEvent.getPlayer();
        var party = SimpleParty.getInstance().getPartyManager().getParty(player);

        if(party == null) return;
        if(!party.isLeader(player)) return;

        var info = player.getServer().getInfo();
        for (ProxiedPlayer member : party.getMembers()) {
            if(member.getServer().getInfo() == info) continue;
            member.connect(info);
        }
    }

    @EventHandler
    public void onChat(ChatEvent chatEvent) {
        var message = chatEvent.getMessage();
        if(!message.startsWith("@p") || !message.startsWith("@party")) return;
        if(!(chatEvent.getSender() instanceof ProxiedPlayer player)) return;

        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) return;

        var edit = player.getName() + " (" + player.getServer().getInfo().getName() + ") > " + message.substring(message.indexOf(" "), message.length()-1);
        party.getMembers().forEach(member -> member.sendMessage(Component.PARTY, Component.text(edit)));
    }
}
