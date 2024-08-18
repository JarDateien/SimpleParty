package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.utils.Math;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class HelpCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        player.sendMessage("party_management");

        switch (Math.search(args)) {
            case 1 -> {
                player.sendMessage("invite_use_description");
                player.sendMessage("accept_use_description");
                player.sendMessage("deny_use_description");
                player.sendMessage("list_use_description");
                player.sendMessage("leave_use_description");
            }

            case 2 -> {
                player.sendMessage("jump_use_description");
                player.sendMessage("promode_use_description");
                player.sendMessage("demote_use_description");
                player.sendMessage("kick_use_description");
                player.sendMessage("join_use_description");
            }

            case 3 -> {
                player.sendMessage("language_use_description");
                player.sendMessage("toggle_use_description");
                player.sendMessage("pull_use_description");
                player.sendMessage("help_use_description");
                player.sendMessage("chat_use_description");
            }
        }
    }
}
