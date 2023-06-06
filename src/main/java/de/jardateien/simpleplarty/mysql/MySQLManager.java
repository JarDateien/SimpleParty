package de.jardateien.simpleplarty.mysql;

import de.jardateien.simpleplarty.SimpleParty;

public class MySQLManager {

    private final MySqlConfiguration mysqlConfig;
    private final MySQL mysql;

    public MySQLManager(SimpleParty party) {
        this.mysqlConfig = new MySqlConfiguration(party.getDataFolder());
        this.mysql = new MySQL(party, this.mysqlConfig);
        this.mysql.connect(null);
    }

}
