package de.jardateien.simpleplarty.language;

import de.jardateien.simpleplarty.utils.Configuration;
import de.jardateien.simpleplarty.utils.DirectoryScanner;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LanguageConfiguration extends Configuration {

    private final File folder;

    @Getter
    private final List<Language> languages;

    public LanguageConfiguration(final File defaultFolder) {
        super();
        this.folder = new File(defaultFolder, "Languages");
        this.folder.mkdirs();
        this.languages = new ArrayList<>();

        this.loadDefault();
        this.loadConfiguration();
    }

    private void loadConfiguration() {
        var scanner = new DirectoryScanner(this.folder).withFilter(file -> file.getName().contains(".yml") && !file.getName().startsWith("-"));
        scanner.scanForFiles(languages -> this.languages.add(new Language(languages, this.configuration)));
    }

    private void loadDefault() {
        var english = new File(folder.getPath(),"English.yml");
        try {
            if(!english.createNewFile()) return;
            var config = this.configuration.load(english);

            config.set("player_isnt_leader", "'§cYou not are the Leader!'");
            config.set("player_isnt_online", "'§e{player} §cisn't online!'");
            config.set("player_is_same", "'§cYou can't interact with your self!'");
            config.set("player_in_other_party", "'§7The player is alredy in the other party!'");
            config.set("player_isnt_in_party", "'§cYou dont have a Party!'");

            config.set("use_invite", "'Use: /party invite <Player>'");
            config.set("use_accept", "'Use: /party accept <Player>'");
            config.set("use_deny", "'Use: /party deny <Player>'");
            config.set("use_list", "'Use: /party list'");
            config.set("use_leave", "'Use: /party leave'");
            config.set("use_jump", "'Use: /party jump'");
            config.set("use_promode", "'Use: /party promode <Player>'");
            config.set("use_demote", "'Use: /party demote <Player>'");
            config.set("use_kick", "'Use: /party kick <Player>'");
            config.set("use_join", "'Use: /party join <Player>'");
            config.set("use_language", "'Use: /party language <language>'");
            config.set("use_toggle", "'Use: /party toggle <settings>'");
            config.set("use_pull", "'Use: /party pull'");
            config.set("use_help", "'Use: /party help'");
            config.set("use_chat", "'Use: /party chat <message>'");

            config.set("use_invite_description", "' §e/party invite <Player> §8| §7Invite players to the party invite'");
            config.set("use_accept_description", "' §e/party accept <Player> §8| §7Take a party request to'");
            config.set("use_deny_description", "' §e/party deny <Player> §8| §7Submit a party request'");
            config.set("use_list_description", "' §e/party list §8| §7List all party members'");
            config.set("use_leave_description", "' §e/party leave §8| §7Leaving a party'");
            config.set("use_jump_description", "' §e/party jump §8| §7Jump to the server of the leader'");
            config.set("use_promode_description", "' §e/party promode <Player> §8| §7Promote a player from the party'");
            config.set("use_demote_description", "' §e/party demote <Player> §8| §7Demote a player from the party'");
            config.set("use_kick_description", "' §e/party kick <Player> §8| §7Kick a player out of the party'");
            config.set("use_join_description", "' §e/party join <Player> §8| §7Enter an open party'");
            config.set("use_language_description", "' §e/party language <language> §8| §7Set your language'");
            config.set("use_toggle_description", "' §e/party toggle <Settings> §8| §7TODO'");
            config.set("use_pull_description", "' §e/party help <1-3> §8| §7List aids'");
            config.set("use_help_description", "' §e/party list §8| §7List all party members'");
            config.set("use_chat_description", "' §e/party chat <message> §8| §7Send a message to the party'");
            this.configuration.save(config, english);
            System.out.print("Loaded");
        } catch (IOException exception) { exception.printStackTrace(); }
    }

    public void save() {
        for (Language language : this.languages) {
            try { this.configuration.save(language.getConfiguration(), language.getFile()); }
            catch (IOException exception) { exception.printStackTrace(); }
        }
    }
}
