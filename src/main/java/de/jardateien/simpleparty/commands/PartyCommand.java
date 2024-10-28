package de.jardateien.simpleparty.commands;

import de.jardateien.simpleparty.commands.manager.CommandManager;
import de.jardateien.simpleparty.commands.subcommands.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyCommand extends Command implements Listener {

    private final CommandManager manager;
    private HelpCommand defaultCommand;

    public PartyCommand() {
        super("party");
        this.manager = new CommandManager();
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer player)) return;

        var command = event.getCursor().toLowerCase();
        if (!command.startsWith("/" + this.getName())) return;

        var args = command.split(" ");
        if (args.length <= 1) {
            event.getSuggestions().addAll(this.defaultCommand.complete(player, args));
            return;
        }

        var subCommand = this.manager.get(args[1]);
        if (subCommand == null) return;

        event.getSuggestions().addAll(subCommand.complete(player, args));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer player)) return;

        if(args.length == 0) {
            this.defaultCommand.execute(player, args);
            return;
        }

        var subCommand = this.manager.get(args[0]);
        if(subCommand == null) {
            this.defaultCommand.execute(player, args);
            return;
        }

        subCommand.execute(player, args);
    }

    public PartyCommand registerSubCommands() {
        this.defaultCommand = new HelpCommand();
        var listCommand = new ListCommand();

        this.manager.registerCommand("requests", new RequestsCommand());    //Finish
        this.manager.registerCommand("withdrew", new WithdrewCommand());    //Finish
        this.manager.registerCommand("decline", new DeclineCommand());      //Finish
        this.manager.registerCommand("promode", new PromodeCommand());      //Finish
        this.manager.registerCommand("private", new PrivateCommand());      //Finish
        this.manager.registerCommand("confirm", new ConfirmCommand());      //Finish
        this.manager.registerCommand("create", new CreateCommand());        //Finish
        this.manager.registerCommand("delete", new DeleteCommand());        //Finish
        this.manager.registerCommand("demote", new DemoteCommand());        //Finish
        this.manager.registerCommand("public", new PublicCommand());        //Finish
        this.manager.registerCommand("accept", new AcceptCommand());        //Finish
        this.manager.registerCommand("invite", new InviteCommand());        //Finish
        this.manager.registerCommand("pardon", new PardonCommand());        //Finish
        this.manager.registerCommand("quick", new QuickCommand());          //Finish
        this.manager.registerCommand("leave", new LeaveCommand());          //Finish
        this.manager.registerCommand("help", this.defaultCommand);          //Finish
        this.manager.registerCommand("ban", new PunishCommand());           //Finish
        this.manager.registerCommand("jump", new JumpCommand());            //Finish
        this.manager.registerCommand("chat", new ChatCommand());            //Finish
        this.manager.registerCommand("kick", new KickCommand());            //Finish
        this.manager.registerCommand("pull", new PullCommand());            //Finish
        this.manager.registerCommand("join", new JoinCommand());            //Finish
        this.manager.registerCommand("list", listCommand);                  //Finish
        this.manager.registerCommand("info", listCommand);                  //Finish
        return this;
    }
}
