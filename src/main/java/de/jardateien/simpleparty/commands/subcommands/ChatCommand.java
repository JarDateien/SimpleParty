package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.SimpleParty;
import de.jardateien.simpleparty.utils.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Arrays;
import java.util.List;

public class ChatCommand extends SubCommand {
    @Override
    public List<String> complete(ProxiedPlayer player, String[] args) { return List.of(""); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, Component.text("§cNutze: /party chat <Nachricht>"));
            return;
        }

        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu hast keine Party!"));
            return;
        }

        var message = new StringBuilder("§e" + String.format("%s", player.getName()) + " §8> §7" );
        for (int i = 1; i < args.length; i++) { message.append(args[i]).append(" "); }
        party.getMembers().forEach(member -> member.sendMessage(Component.PARTY, Component.text(message.toString())));
    }
}
