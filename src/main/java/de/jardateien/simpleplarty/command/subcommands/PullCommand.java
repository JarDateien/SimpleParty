package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PullCommand extends SubCommand {
    public PullCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = this.partyManager.getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_in_party"));
            return;
        }

        if(!party.isLeader(player)) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_leader"));
            return;
        }

        var serverInfo = player.getServer().getInfo();

        for (ProxiedPlayer member : party.getMembers()) {
            if(member.getServer().getInfo() == serverInfo) continue;
            member.connect(serverInfo);
        }
    }
}
