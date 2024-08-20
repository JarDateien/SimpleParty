package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.party.Party;
import de.jardateien.simpleparty.SimpleParty;
import de.jardateien.simpleparty.utils.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CreateCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var manager = SimpleParty.getInstance().getPartyManager();
        if(manager.getParty(player) != null) {
            player.sendMessage(Component.text("Hast schon ne Party"));
            return;
        }

        manager.joinParty(player, new Party(player));
        player.sendMessage(Component.PARTY, Component.text("<gray>Du hast erfolgreich eine <bold>Party</bold> erstellt</gray>"));
    }
}
