package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class HelpCommand extends SubCommand {
    public HelpCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var search = this.search(args);
        player.sendMessage(Component.PARTY, this.languageManager.get(player, "party_management", String.valueOf(search)));

        switch (search) {
            case 1 -> {
                player.sendMessage(this.languageManager.get(player, "invite_use_description"));
                player.sendMessage(this.languageManager.get(player, "accept_use_description"));
                player.sendMessage(this.languageManager.get(player, "deny_use_description"));
                player.sendMessage(this.languageManager.get(player, "list_use_description"));
                player.sendMessage(this.languageManager.get(player, "leave_use_description"));
            }

            case 2 -> {
                player.sendMessage(this.languageManager.get(player, "jump_use_description"));
                player.sendMessage(this.languageManager.get(player, "promode_use_description"));
                player.sendMessage(this.languageManager.get(player, "demote_use_description"));
                player.sendMessage(this.languageManager.get(player, "kick_use_description"));
                player.sendMessage(this.languageManager.get(player, "join_use_description"));
            }

            case 3 -> {
                player.sendMessage(this.languageManager.get(player, "language_use_description"));
                player.sendMessage(this.languageManager.get(player, "toggle_use_description"));
                player.sendMessage(this.languageManager.get(player, "pull_use_description"));
                player.sendMessage(this.languageManager.get(player, "help_use_description"));
                player.sendMessage(this.languageManager.get(player, "chat_use_description"));
            }
        }
    }

    private int search(String[] args) {
        if(args.length <= 1) return 1;
        try { return Math.min(Integer.parseInt(args[1]), 3); }
        catch (NumberFormatException exception) { return 1; }
    }
}
