package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PullCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) {
            player.sendMessage("");
            return;
        }

        if(!party.isLeader(player)) {
            player.sendMessage("");
            return;
        }

        var serverInfo = player.getServer().getInfo();
        for (ProxiedPlayer member : party.getMembers()) {
            if(member.getServer().getInfo() == serverInfo) continue;
            member.connect(serverInfo);
        }
    }
}
