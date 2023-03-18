package de.jardateien.simpleplarty.party;

import de.jardateien.simpleplarty.utils.Component;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Party {

    @Setter
    private ProxiedPlayer leader;
    private final List<ProxiedPlayer> members;
    private final List<ProxiedPlayer> requests;
    private final List<ProxiedPlayer> moderators;
    private final List<ProxiedPlayer> banned;

    @Setter
    private boolean openParty;

    public Party(final ProxiedPlayer player) {
        this.leader = player;
        this.members = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.moderators = new ArrayList<>();
        this.banned = new ArrayList<>();
        this.members.add(player);
        this.openParty = false;
    }

    public void sendLeader(final String msg) { this.leader.sendMessage(Component.PARTY, Component.text(msg)); }

    public void sendMembers(final String msg) {
        var text = new TextComponent(msg);
        this.members.forEach(member -> member.sendMessage(Component.PARTY, text));
    }

    public void sendTeam(final String msg) {
        var text = new TextComponent(msg);
        this.moderators.forEach(mod -> mod.sendMessage(Component.PARTY, text));
        this.leader.sendMessage(Component.PARTY, text);
    }

    public void addMember(final ProxiedPlayer player) { this.members.add(player); }
    public void removeMember(final ProxiedPlayer player) { this.members.remove(player); }

    public void addRequests(final ProxiedPlayer player) { this.requests.add(player); }
    public void removeRequests(final ProxiedPlayer player) { this.requests.remove(player); }

    public void addModerator(final ProxiedPlayer player) { this.moderators.add(player); }
    public void removeModerator(final ProxiedPlayer player) { this.moderators.remove(player); }

    public void addBanned(final ProxiedPlayer player) { this.banned.add(player); }
    public void removeBanned(final ProxiedPlayer player) { this.banned.remove(player); }

    public int getSize() { return this.members.size(); }
    public boolean isLeader(final ProxiedPlayer player) { return this.leader == player; }
    public boolean isInParty(final ProxiedPlayer player) { return this.members.contains(player); }
    public boolean hasRequest(final ProxiedPlayer player) { return this.requests.contains(player); }
    public boolean isModerator(final ProxiedPlayer player) { return this.moderators.contains(player); }

}
