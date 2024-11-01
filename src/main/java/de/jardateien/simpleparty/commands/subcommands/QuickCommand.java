package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import de.jardateien.simpleparty.utils.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.List;

public class QuickCommand extends SubCommand {

    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) {
        return List.of("");
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var manager = SimpleParty.getInstance().getPartyManager();
        var party = manager.randomParty();
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text("§cEs gibt keine Öffendliche Partys"));
            return;
        }

        manager.joinParty(player, party);
        player.sendMessage(Component.PARTY, Component.text("Du bist die Party von " + party.getLeader().getName() + " beigetreten!"));
    }
}
