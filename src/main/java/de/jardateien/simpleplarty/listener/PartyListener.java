package de.jardateien.simpleplarty.listener;

import de.jardateien.simpleplarty.party.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyListener implements Listener {

    private final PartyManager partyManager;
    public PartyListener(PartyManager partyManager) { this.partyManager = partyManager; }

    @EventHandler
    public void onSwitch(ServerSwitchEvent switchEvent) {
        var player = switchEvent.getPlayer();
        var party = this.partyManager.getParty(player);
        if(party == null) return;

        var serverInfo = player.getServer().getInfo();
        if(serverInfo.getName().contains("lobby")) return;

        for (ProxiedPlayer member : party.getMembers()) {
            if(member.getServer().getInfo() == serverInfo) continue;
            member.connect(serverInfo);
        }
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent disconnectEvent) {
        var player = disconnectEvent.getPlayer();
        var party = this.partyManager.getParty(player);
        if(party == null) return;

        this.partyManager.removeParty(player);
    }
}
