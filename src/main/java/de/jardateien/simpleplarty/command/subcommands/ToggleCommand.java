package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;
import java.util.List;

public class ToggleCommand extends SubCommand {

    private final List<String> args;

    public ToggleCommand(ControllManager controllManager) {
        super(controllManager);
        this.args = Arrays.asList("msg","pull", "invite", "notify");
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "use_toggle"));
            return;
        }

        if(!this.args.contains(args[1].toLowerCase())) {
            player.sendMessage(Component.PARTY, Component.text("§7/party toggle <msg, pull, invite, notify>"));
            return;
        }

    }
}
