package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.party.PartyManager;
import de.jardateien.simpleplarty.utils.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ListCommand extends SubCommand {
    public ListCommand(PartyManager partyManager) { super(partyManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = this.partyManager.getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu bist in keiner Party!"));
            return;
        }

        player.sendMessage(Component.PARTY, Component.text("§eMitglieder der Party von §a" + party.getLeader().getName() + " §7(" + party.getSize() + ")"));
        party.getModerators().forEach(member -> player.sendMessage(Component.text("§8- §a" + member.getName() + " §c(Mod)  §8| §7" + member.getServer().getInfo().getName())));

        for (ProxiedPlayer member : party.getModerators()) {
            if(party.isLeader(member)) continue;
            if(party.isModerator(member)) continue;
            player.sendMessage(Component.text("§8- §a" + member.getName() + " §8| §7" + member.getServer().getInfo().getName()));
        }
    }
}
