package de.jardateien.simpleparty.events;

import de.jardateien.simpleparty.party.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PartyPlayerBanEvent extends Event {

    private final ProxiedPlayer sender;
    private final ProxiedPlayer player;
    private final Party party;

    public PartyPlayerBanEvent(Party party, ProxiedPlayer sender, ProxiedPlayer player) {
        this.party = party;
        this.sender = sender;
        this.player = player;
    }

}
