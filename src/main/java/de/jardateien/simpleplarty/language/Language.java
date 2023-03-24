package de.jardateien.simpleplarty.language;

import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Language {

    private final File file;
    private final String name;
    private Configuration configuration;
    private final Map<String, String> values;
    private final ConfigurationProvider provider;

    public Language(File file, ConfigurationProvider provider) {
        this.file = file;
        this.provider = provider;
        this.values = new HashMap<>();
        this.name = file.getName().replace(".yml", "");

        this.loadValues();
    }

    private void loadValues() {
        try {
            this.configuration = this.provider.load(this.file);
            this.configuration.getKeys().forEach(key -> this.values.put(key, this.configuration.getString(key)));
        } catch (IOException exception) { exception.printStackTrace(); }
    }

    public String get(String key) { return this.values.get(key); }
}
