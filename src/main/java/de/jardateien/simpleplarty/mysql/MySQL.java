package de.jardateien.simpleplarty.mysql;

import de.jardateien.simpleplarty.SimpleParty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MySQL {

    private final String adresse;
    private final String password;
    private final String database;
    private final String username;

    private final Logger logger;
    private Connection connection;

    public MySQL(SimpleParty party, MySqlConfiguration configuration) {
        var config = configuration.getConfig();
        this.adresse = config.getString("Adresse");
        this.database = config.getString("Database");
        this.password = config.getString("Password");
        this.username = config.getString("Username");

        this.logger = party.getLogger();
    }

    public boolean isConnected() { return this.connection != null; }

    public void connect(Runnable callback) {
        try {
            if (this.isConnected()) {
                this.logger.warning("MySQL is connected!");
                return;
            }

            String url = "jdbc:mysql://" + this.adresse + ":" + 3306 + "/" + this.database + "?autoReconnect=true";
            this.connection = DriverManager.getConnection(url, this.username, this.password);
            this.logger.info("The Connection with MySQL was connected!");

            if(callback == null) return;
            callback.run();
        } catch (SQLException exception) { this.logger.severe("The Connection with MySQL failed! " + exception.getMessage()); }
    }

    public void close() {
        try {
            if (!this.isConnected()) {
                this.logger.warning("MySQL isn't connected!");
                return;
            }

            this.connection.close();
            this.logger.info("The Connection with MySQL was disconnected!");
        } catch (SQLException exception) { this.logger.severe("Error with disconnect with MySQL!" + exception.getMessage()); }
    }

    public void update(String qry) {
        try {
            var statement = this.connection.createStatement();
            statement.executeUpdate(qry);
            statement.close();
        } catch (SQLException exception) { this.logger.severe("Error: " + exception.getMessage()); }
    }

    public ResultSet query(String qry) {
        try {
            var statement = this.connection.createStatement();
            return statement.executeQuery(qry);
        } catch (SQLException exception) { this.logger.severe("Error: " + exception.getMessage()); }
        return null;
    }
}
