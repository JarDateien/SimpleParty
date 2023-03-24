package de.jardateien.simpleplarty.language;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManager {

    private final LanguageConfiguration configuration;
    private final Map<Language, List<ProxiedPlayer>> languages;
    private final CommandSender languageLogger;

    public LanguageManager(Plugin plugin) {
        this.languageLogger = plugin.getProxy().getConsole();
        this.configuration = new LanguageConfiguration(plugin.getDataFolder());
        this.languages = new HashMap<>();

        var languageList = this.configuration.getLanguages();
        if(languageList.isEmpty()) {
            this.languageLogger.sendMessage("Language is Empty!");
            return;
        }

        languageList.forEach(language -> {
            this.languageLogger.sendMessage(language.getName() + " loaded!");
            this.languages.put(language, new ArrayList<>());
        });
    }

    public String get(final ProxiedPlayer player, final String key, String replace) {
        return this.language(player).get(key).replace("%s", replace);
    }

    public String get(final ProxiedPlayer player, final String key) {
        return this.language(player).get(key);
    }

    public void add(final ProxiedPlayer player, final String languageKey) {
        var language = this.language(player);
        if(language != null) this.languages.get(language).remove(player);
        language = this.language(languageKey);
        if(language == null) return;
        this.languages.get(language).add(player);
    }

    public void remove(final ProxiedPlayer player) {
        var language = this.language(player);
        if(language == null) return;
        this.languages.get(language).remove(player);
    }

    public void save() { this.configuration.save(); }

    private Language language(final ProxiedPlayer player) {
        for (Language language : this.languages.keySet()) {
            if(!this.languages.get(language).contains(player)) continue;
            return language;
        }
        return null;
    }

    private Language language(final String languageKey) {
        for (Language language : this.languages.keySet()) {
            if(!language.getName().contains(languageKey)) continue;
            return language;
        }
        return null;
    }

}
