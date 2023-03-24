package de.jardateien.simpleplarty.utils;

import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public abstract class Configuration {

    protected final ConfigurationProvider configuration;
    protected Configuration() { this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class); }

}
