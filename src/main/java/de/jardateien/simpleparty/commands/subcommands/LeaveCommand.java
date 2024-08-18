package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LeaveCommand extends SubCommand {
    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var manager = SimpleParty.getInstance().getPartyManager();

        var party = manager.getParty(player);
        if(party == null) {
            player.sendMessage("");
            return;
        }

        manager.leaveParty(player);

        if(party.isLeader(player) && party.getSize() >= 2) {
            var leader = party.setRandomLeader();
            party.getMembers().forEach(member -> member.sendMessage(""));
            if(party.isModerator(leader)) party.removeModerator(leader);
            return;
        }

        manager.delete(party);
    }
}
