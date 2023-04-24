package de.jardateien.simpleplarty.command.manager;

import de.jardateien.simpleplarty.language.LanguageManager;
import de.jardateien.simpleplarty.party.PartyManager;
import de.jardateien.simpleplarty.utils.ControllManager;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Getter
public abstract class SubCommand {
    protected final PartyManager partyManager;
    protected final LanguageManager languageManager;
//    protected final String description;
//    protected final String use;
//
//    public SubCommand(String use, String description) {
//        this.use = use;
//        this.description = description;
//    }

    public SubCommand(ControllManager controllManager) {
        this.partyManager = controllManager.getPartyManager();
        this.languageManager = controllManager.getLanguageManager();
    }

    public abstract void execute(ProxiedPlayer player, String[] args);

}
