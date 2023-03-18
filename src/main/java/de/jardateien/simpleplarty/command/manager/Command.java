package de.jardateien.simpleplarty.command.manager;

public abstract class Command extends net.md_5.bungee.api.plugin.Command {
    protected final CommandManager commandManager;

    public Command(String name) {
        super(name);
        this.commandManager = new CommandManager();
    }

    public abstract void registerSubCommands();
}
