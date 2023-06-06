package de.jardateien.simpleplarty.mysql;

import de.jardateien.simpleplarty.utils.Configuration;
import java.io.File;
import java.io.IOException;

public class MySqlConfiguration extends Configuration {

    private final File file;

    public MySqlConfiguration(File defaultFolder) {
        this.file = new File(defaultFolder.getPath(), "MySQL.yml");
        this.loadConfig(defaultFolder);
    }

    private void loadConfig(File defaultFolder) {
        defaultFolder.mkdirs();
        try {
            if(!this.file.createNewFile()) return;
            var config = this.configuration.load(this.file);
            config.set("Adresse", "localhost");
            config.set("Database", "localbase");
            config.set("Username", "localname");
            config.set("Password", "localpass");
            this.configuration.save(config, this.file);
            System.out.print("Loaded");
        } catch (IOException exception) { exception.printStackTrace(); }
    }

    public net.md_5.bungee.config.Configuration getConfig() {
        try { return this.configuration.load(this.file); }
        catch (IOException exception) { exception.printStackTrace(); }
        return null;
    }
}
