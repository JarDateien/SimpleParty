package de.jardateien.simpleparty.party;

import de.jardateien.simpleparty.utils.Math;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

@Getter
public class Party {

    @Setter
    private ProxiedPlayer leader;
    private final List<ProxiedPlayer> members;
    private final List<ProxiedPlayer> requests;
    private final List<ProxiedPlayer> moderators;
    private final Set<ProxiedPlayer> banned;
    private final Map<ProxiedPlayer, Runnable> actions;

    @Setter
    private boolean openParty;

    public Party(@NonNull final ProxiedPlayer player) {
        this.leader = player;
        this.members = new LinkedList<>();
        this.requests = new LinkedList<>();
        this.moderators = new LinkedList<>();
        this.actions = new LinkedHashMap<>();
        this.banned = new LinkedHashSet<>();
        this.members.add(player);
        this.openParty = false;
    }

    public ProxiedPlayer setRandomLeader() {
        return this.leader = this.moderators.isEmpty() ? this.members.get(Math.getRandom(this.members.size()-1)) : this.moderators.get(Math.getRandom(this.moderators.size()-1));
    }

    public void setAction(ProxiedPlayer player, Runnable runnable) { this.actions.put(player, runnable);}
    public Runnable getAction(ProxiedPlayer player) { return this.actions.get(player); }
    public void removeAction(ProxiedPlayer player) { this.actions.remove(player); }

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
    public boolean isBanned(final ProxiedPlayer player) { return this.banned.contains(player); }
    public boolean hasRequested(final ProxiedPlayer player) { return this.requests.contains(player); }
    public boolean isModerator(final ProxiedPlayer player) { return this.moderators.contains(player); }

}
