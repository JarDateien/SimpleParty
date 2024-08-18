package de.jardateien.simpleparty.party;

import de.jardateien.simpleparty.utils.Math;
import lombok.NonNull;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class PartyManager {

    private final List<Party> openParties;
    private final Map<ProxiedPlayer, Party> parties;
    public PartyManager() {
        this.parties = new LinkedHashMap<>();
        this.openParties = new LinkedList<>();
    }

    public Collection<Party> getValues() {
        return this.parties.values();
    }

    public Party randomParty() { return this.openParties.isEmpty() ? null : this.openParties.get(Math.getRandom(this.openParties.size()-1)); }

    public void publicParty(final Party party) {
        if(party.isOpenParty()) return;

        this.openParties.add(party);
        party.setOpenParty(true);
    }

    public void privateParty(final Party party) {
        if(!party.isOpenParty()) return;

        this.openParties.remove(party);
        party.setOpenParty(false);
    }

    public void joinParty(@NonNull final ProxiedPlayer player, @NonNull final Party party) {
        var old = this.parties.get(player);
        if(party == old) return;

        if(old != null) {
            old.removeMember(player);
            old.removeModerator(player);
        }

        if(!party.isInParty(player)) party.addMember(player);
        this.parties.put(player, party);
    }

    public void leaveParty(@NonNull final ProxiedPlayer player) {
        var party = this.parties.get(player);
        if(party == null) return;

        party.removeMember(player);
        this.parties.remove(player);

        if(!party.isModerator(player)) return;
        party.removeModerator(player);
    }

    public void delete(final Party party) {
        party.getBanned().clear();
        party.getRequests().clear();
        this.openParties.remove(party);
        var kickedList = new LinkedList<>(party.getMembers());
        kickedList.forEach(this::leaveParty);
    }

    public Party getParty(final ProxiedPlayer player) { return this.parties.get(player); }

}
