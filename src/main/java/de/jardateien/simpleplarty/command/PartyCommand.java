package de.jardateien.simpleplarty.command;

import de.jardateien.simpleplarty.command.manager.Command;
import de.jardateien.simpleplarty.command.subcommands.*;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyCommand extends Command {


    public PartyCommand(ControllManager controllManager) {
        super("party");
        this.registerSubCommands(controllManager);
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
    public void registerSubCommands(ControllManager controllManager) {
        this.commandManager.registerCommand("language", new LanguageCommand(controllManager));
        this.commandManager.registerCommand("promode", new PromodeCommand(controllManager));
        this.commandManager.registerCommand("demote", new DemoteCommand(controllManager));
        this.commandManager.registerCommand("accept", new AcceptCommand(controllManager));
        this.commandManager.registerCommand("invite", new InviteCommand(controllManager));
        this.commandManager.registerCommand("toggle", new ToggleCommand(controllManager));
        this.commandManager.registerCommand("leave", new LeaveCommand(controllManager));
        this.commandManager.registerCommand("jump", new JumpCommand(controllManager));
        this.commandManager.registerCommand("chat", new ChatCommand(controllManager));
        this.commandManager.registerCommand("deny", new DenyCommand(controllManager));
        this.commandManager.registerCommand("kick", new KickCommand(controllManager));
        this.commandManager.registerCommand("pull", new PullCommand(controllManager));
        this.commandManager.registerCommand("help", new HelpCommand(controllManager));
        this.commandManager.registerCommand("list", new ListCommand(controllManager));
        this.commandManager.registerCommand("join", new JoinCommand(controllManager));
    }
}
