package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ChatCommand extends SubCommand {
    public ChatCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "use_chat_help"));
            return;
        }

        var party = this.partyManager.getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "player_isnt_in_party"));
            return;
        }

        var message = new StringBuilder();
        for (int i = 1; i < args.length; i++) { message.append(args[i]).append(" "); }

        party.sendMembers("§e" + player.getName() + " §8> §7" + message);
    }
}
