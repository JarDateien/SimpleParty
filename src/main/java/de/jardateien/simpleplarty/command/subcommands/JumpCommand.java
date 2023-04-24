package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class JumpCommand extends SubCommand {
    public JumpCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = this.partyManager.getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text("§7§cDu bist in keiner Party!"));
            return;
        }

        var leader= party.getLeader();
        if(party.isLeader(player)) {
            player.sendMessage(Component.PARTY, Component.text("§cDu kannst dir selber nicht nach Springen!"));
            return;
        }

        var leaderServer = leader.getServer();
        if(player.getServer() == leaderServer) {
            player.sendMessage(Component.PARTY, Component.text("§cDu bist bereits auf dem Server!"));
            return;
        }

        player.connect(leaderServer.getInfo());
    }
}
