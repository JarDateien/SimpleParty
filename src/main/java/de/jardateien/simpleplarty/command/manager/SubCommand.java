package de.jardateien.simpleplarty.command.manager;

import de.jardateien.simpleplarty.party.PartyManager;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Getter
public abstract class SubCommand {
    protected final PartyManager partyManager;
//    protected final String description;
//    protected final String use;
//
//    public SubCommand(String use, String description) {
//        this.use = use;
//        this.description = description;
//    }

    public SubCommand(PartyManager partyManager) { this.partyManager = partyManager; }

    public abstract void execute(ProxiedPlayer player, String[] args);

}
