package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.party.PartyManager;
import de.jardateien.simpleplarty.utils.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PullCommand extends SubCommand {
    public PullCommand(PartyManager partyManager) { super(partyManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = this.partyManager.getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu bist in keiner Party!"));
            return;
        }

        if(!party.isLeader(player)) {
            player.sendMessage(Component.PARTY, Component.text("§cDu bist nicht die Leitung!"));
            return;
        }

        var serverInfo = player.getServer().getInfo();

        for (ProxiedPlayer member : party.getMembers()) {
            if(member.getServer().getInfo() == serverInfo) continue;
            member.connect(serverInfo);
        }
    }
}
