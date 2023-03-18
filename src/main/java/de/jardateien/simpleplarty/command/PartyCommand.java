package de.jardateien.simpleplarty.command;

import de.jardateien.simpleplarty.command.manager.Command;
import de.jardateien.simpleplarty.command.subcommands.*;
import de.jardateien.simpleplarty.party.PartyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyCommand extends Command {

    private final PartyManager partyManager;

    public PartyCommand(PartyManager partyManager) {
        super("party");
        this.partyManager = partyManager;
        this.registerSubCommands();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer player)) return;

        if(args.length == 0) {
            this.commandManager.get("help").execute(player, args);
            return;
        }

        var subCommand = this.commandManager.get(args[0]);
        if(subCommand == null) {
            this.commandManager.get("help").execute(player, args);
            return;
        }

        subCommand.execute(player, args);
    }

    @Override
    public void registerSubCommands() {
        this.commandManager.registerCommand("promode", new PromodeCommand(this.partyManager));
        this.commandManager.registerCommand("demote", new DemoteCommand(this.partyManager));
        this.commandManager.registerCommand("accept", new AcceptCommand(this.partyManager));
        this.commandManager.registerCommand("invite", new InviteCommand(this.partyManager));
        this.commandManager.registerCommand("toggle", new ToggleCommand(this.partyManager));
        this.commandManager.registerCommand("leave", new LeaveCommand(this.partyManager));
        this.commandManager.registerCommand("jump", new JumpCommand(this.partyManager));
        this.commandManager.registerCommand("chat", new ChatCommand(this.partyManager));
        this.commandManager.registerCommand("deny", new DenyCommand(this.partyManager));
        this.commandManager.registerCommand("kick", new KickCommand(this.partyManager));
        this.commandManager.registerCommand("pull", new PullCommand(this.partyManager));
        this.commandManager.registerCommand("help", new HelpCommand(this.partyManager));
        this.commandManager.registerCommand("list", new ListCommand(this.partyManager));
        this.commandManager.registerCommand("join", new JoinCommand(this.partyManager));
    }
}
