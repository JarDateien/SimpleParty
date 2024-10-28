package de.jardateien.simpleparty.events;

import de.jardateien.simpleparty.party.Party;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

@Getter
public class PartyPlayerKickEvent extends Event {

    private final ProxiedPlayer sender;
    private final ProxiedPlayer kicked;
    private final Party party;

    public PartyPlayerKickEvent(Party party, ProxiedPlayer sender, ProxiedPlayer kicked) {
        this.party = party;
        this.sender = sender;
        this.kicked = kicked;
    }

}
