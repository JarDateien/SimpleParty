package de.jardateien.simpleplarty.command.subcommands;


import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.party.PartyManager;
import de.jardateien.simpleplarty.utils.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LeaveCommand extends SubCommand {
    public LeaveCommand(PartyManager partyManager) { super(partyManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = this.partyManager.getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text("§cDu bist in keiner Party!"));
            return;
        }

        player.sendMessage(Component.PARTY, Component.text("§7Du hast die Party verlassen!"));
        party.sendMembers("§a" + player.getName() + " §7hat die Party Verlassen!");
        this.partyManager.removeParty(player);
    }
}
