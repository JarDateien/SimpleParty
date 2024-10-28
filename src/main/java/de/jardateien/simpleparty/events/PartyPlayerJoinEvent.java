package de.jardateien.simpleparty.events;

import de.jardateien.simpleparty.party.Party;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

@Getter
public class PartyPlayerJoinEvent extends Event {

    private final Party party;
    private final ProxiedPlayer player;

    public PartyPlayerJoinEvent(Party party, ProxiedPlayer player) {
        this.party = party;
        this.player = player;
    }

}
