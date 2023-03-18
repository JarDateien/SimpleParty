package de.jardateien.simpleplarty.party;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class PartyManager {

    private final Map<ProxiedPlayer, Party> partys;
    private final List<Party> openPartys;

    public PartyManager() {
        this.partys = new HashMap<>();
        this.openPartys = new ArrayList<>();
    }

    public void setParty(final ProxiedPlayer player, final Party party) {
        this.partys.put(player, party);
        if(party.isInParty(player)) return;

        party.sendMembers("§a" + player.getName() + " §7hat die Party betreten");
        party.addMember(player);
    }

    public void removeParty(final ProxiedPlayer player) {
        var party = this.getParty(player);
        if(party == null) return;

        party.removeMember(player);
        this.partys.remove(player);

        if(party.isLeader(player) && party.getSize() >= 2) {
            var leader = this.getMember(party);
            party.setLeader(leader);
            party.sendMembers("§a" + leader.getName() + " §7wurde zur Party-Leitung ernannt!");
            if(!party.isModerator(leader)) return;
            party.removeModerator(leader);
            return;
        }

        party.sendMembers("§cDie Party wurde aufgelöst!");
        for (ProxiedPlayer member : party.getMembers()) {
            party.removeMember(member);
            this.partys.remove(member);
        }
    }

    public boolean isPartyOpen(final Party party) { return this.openPartys.contains(party); }
    public Party getParty(final ProxiedPlayer player) { return this.partys.get(player); }
    public Set<ProxiedPlayer> getPlayers() { return this.partys.keySet(); }

    private int getRandom(final int min, final int max) { return new Random().nextInt(max - min + 1) + min; }
    private ProxiedPlayer getMember(final Party party) { return party.getMembers().get(this.getRandom(0, party.getSize())); }
}
