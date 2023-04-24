package de.jardateien.simpleplarty.utils;

import de.jardateien.simpleplarty.SimpleParty;
import de.jardateien.simpleplarty.language.LanguageManager;
import de.jardateien.simpleplarty.mysql.MySQLManager;
import de.jardateien.simpleplarty.party.PartyManager;
import lombok.Getter;

@Getter
public class ControllManager {

    private final PartyManager partyManager;
    private final LanguageManager languageManager;
    private final MySQLManager mysqlManager;

    public ControllManager(SimpleParty party) {
        this.partyManager = new PartyManager();
        this.languageManager = new LanguageManager(party);
        this.mysqlManager = new MySQLManager(party);
    }
}
