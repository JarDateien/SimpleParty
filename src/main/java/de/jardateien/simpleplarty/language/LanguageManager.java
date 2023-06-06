package de.jardateien.simpleplarty.language;

import de.jardateien.simpleplarty.utils.ColorUtil;
import de.jardateien.simpleplarty.utils.Component;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class LanguageManager {

    private final LanguageConfiguration configuration;
    private final Map<Language, List<ProxiedPlayer>> languages;
    private final Logger logger;

    public LanguageManager(Plugin plugin) {
        this.logger = plugin.getLogger();
        this.configuration = new LanguageConfiguration(plugin.getDataFolder());
        this.languages = new HashMap<>();

        var languageList = this.configuration.getLanguages();
        if(languageList.isEmpty()) {
            this.logger.severe("Language is Empty!");
            return;
        }

        languageList.forEach(language -> {
            this.logger.info(language.getName() + " loaded!");
            this.languages.put(language, new ArrayList<>());
        });
    }

    public TextComponent get(final ProxiedPlayer player, final String key, final String replace) {
        return Component.text(ColorUtil.translate(player, this.language(player).get(key).replace("{player}", replace)));
    }

    public TextComponent get(final ProxiedPlayer player, final String key) {
        return Component.text(ColorUtil.translate(player, this.language(player).get(key)));
    }

    public List<Language> getLanguages() { return this.languages.keySet().stream().toList(); }

    public void add(final ProxiedPlayer player, final Language languageKey) {
        var language = this.language(player);
        if(language != null) this.languages.get(language).remove(player);
        if(languageKey == null) return;
        this.languages.get(languageKey).add(player);
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

    public Language language(final String languageKey) {
        for (Language language : this.languages.keySet()) {
            if(!language.getName().toLowerCase().contains(languageKey.toLowerCase())) continue;
            return language;
        }
        return null;
    }

}
