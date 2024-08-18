package de.jardateien.simpleparty.commands.manager;

import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Getter
public abstract class SubCommand {

    public abstract void execute(ProxiedPlayer player, String[] args);

}
