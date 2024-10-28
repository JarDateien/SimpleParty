package de.jardateien.simpleparty.events;

import de.jardateien.simpleparty.party.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PartyPlayerLeaveEvent extends Event {

    private final Party party;
    private final ProxiedPlayer player;

    public PartyPlayerLeaveEvent(Party party, ProxiedPlayer player) {
        this.party = party;
        this.player = player;
    }

}
