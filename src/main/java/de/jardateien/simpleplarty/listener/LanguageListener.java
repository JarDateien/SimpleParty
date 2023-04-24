package de.jardateien.simpleplarty.listener;

import de.jardateien.simpleplarty.language.LanguageManager;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LanguageListener implements Listener {

    private final LanguageManager languageManager;

    public LanguageListener(ControllManager controllManager) { this.languageManager = controllManager.getLanguageManager(); }

    @EventHandler
    public void onConnect(PostLoginEvent loginEvent) {
        var language = this.languageManager.language("Germany");
        this.languageManager.add(loginEvent.getPlayer(), language);
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent disconnectEvent) {
        this.languageManager.remove(disconnectEvent.getPlayer());
    }

}
