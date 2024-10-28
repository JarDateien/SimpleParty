package de.jardateien.simpleparty.commands.manager;

import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

@Getter
public abstract class SubCommand {

    public abstract List<String> complete(ProxiedPlayer player, String[] args);
    public abstract void execute(ProxiedPlayer player, String[] args);

}
