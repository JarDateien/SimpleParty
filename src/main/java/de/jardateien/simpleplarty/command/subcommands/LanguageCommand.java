package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LanguageCommand extends SubCommand {
    public LanguageCommand(ControllManager controllManager) { super(controllManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, this.languageManager.get(player, "use_language"));
            return;
        }

        var language = this.languageManager.language(args[1]);
        if(language == null) {
            var languageList = new StringBuilder();
            this.languageManager.getLanguages().forEach(key -> languageList.append(key.getName()).append(", "));
            player.sendMessage(Component.PARTY, Component.text(languageList.toString()));
            return;
        }

        this.languageManager.add(player, language);
        player.sendMessage(Component.PARTY, Component.text("langeuage: " + language.getName()));
    }
}
