package de.jardateien.simpleplarty.party;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class PartyManager {

    private final Map<ProxiedPlayer, Party> partys;

    public PartyManager() {
        this.partys = new HashMap<>();
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

    public Party getParty(final ProxiedPlayer player) { return this.partys.get(player); }

    private int getRandom(final int max) { return new Random().nextInt(max + 1); }
    private ProxiedPlayer getMember(final Party party) { return party.getMembers().get(this.getRandom(party.getSize())); }
}
