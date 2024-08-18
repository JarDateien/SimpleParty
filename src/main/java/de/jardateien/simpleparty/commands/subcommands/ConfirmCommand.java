package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.utils.Component;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ConfirmCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu hast keine Party!"));
            return;
        }

        if(!party.isLeader(player) && !party.isModerator(player)) {
            player.sendMessage(Component.PARTY,  Component.text("§cDu hast keine Rechte!"));
            return;
        }

        var action = party.getAction(player);
        if(action == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu hast keine Action!"));
            return;
        }

        action.run();
        party.removeAction(player);
    }
}
