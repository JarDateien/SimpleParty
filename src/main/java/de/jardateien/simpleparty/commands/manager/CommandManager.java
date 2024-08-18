package de.jardateien.simpleparty.commands.manager;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, SubCommand> subcommands;
    public CommandManager() { this.subcommands = new HashMap<>(); }
    public void registerCommand(String cmd, SubCommand subCommand) { this.subcommands.put(cmd.toLowerCase(), subCommand); }
    public SubCommand get(String command) { return this.subcommands.get(command.toLowerCase()); }

}
