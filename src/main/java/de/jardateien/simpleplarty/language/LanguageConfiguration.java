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
            config.set("player_isnt_leader", "§cYou not are the Leader!");
            config.set("player_isnt_online", "§e{player} §cisnt online!");
            config.set("player_is_same", "§cYou cant interact with your self!");
            config.set("player_in_other_party", "§7The player is alredy in the other party!");
            config.set("player_isnt_in_party", "§cYou dont have a Party!");
            config.set("player_already_in_party", "§cYou are already in a party!");
            config.set("player_dont_have_party", "§cThis player dont have a party!");
            config.set("player_dont_have_request", "§cYou not invited to this party!");
            config.set("player_cant_remove_leader", "§cYou can't remove this!");
            config.set("player_is_in_my_party", "§cThe player is not in your party!");
            config.set("player_is_not_moderator", "§cThe player is not a moderator!");
            config.set("player_is_not_longer_moderator", "§a{player} §7is no longer a moderator!");

            config.set("use_invite_help", "Use: /party invite <Player>");
            config.set("use_accept_help", "Use: /party accept <Player>");
            config.set("use_deny_help", "Use: /party deny <Player>");
            config.set("use_list_help", "Use: /party list");
            config.set("use_leave_help", "Use: /party leave");
            config.set("use_jump_help", "Use: /party jump");
            config.set("use_promode_help", "Use: /party promode <Player>");
            config.set("use_demote_help", "Use: /party demote <Player>");
            config.set("use_kick_help", "Use: /party kick <Player>");
            config.set("use_join_help", "Use: /party join <Player>");
            config.set("use_language_help", "Use: /party language <language>");
            config.set("use_toggle_help", "Use: /party toggle <settings>");
            config.set("use_pull_help", "Use: /party pull");
            config.set("use_help_help", "Use: /party help");
            config.set("use_chat_help", "Use: /party chat <message>");

            config.set("info_invite_description", " §e/party invite <Player> §8| §7Invite players to the party invite");
            config.set("info_accept_description", " §e/party accept <Player> §8| §7Take a party request to");
            config.set("info_deny_description", " §e/party deny <Player> §8| §7Submit a party request");
            config.set("info_list_description", " §e/party list §8| §7List all party members");
            config.set("info_leave_description", " §e/party leave §8| §7Leaving a party");
            config.set("info_jump_description", " §e/party jump §8| §7Jump to the server of the leader");
            config.set("info_promode_description", " §e/party promode <Player> §8| §7Promote a player from the party");
            config.set("info_demote_description", " §e/party demote <Player> §8| §7Demote a player from the party");
            config.set("info_kick_description", " §e/party kick <Player> §8| §7Kick a player out of the party");
            config.set("info_join_description", " §e/party join <Player> §8| §7Enter an open party");
            config.set("info_language_description", " §e/party language <language> §8| §7Set your language");
            config.set("info_toggle_description", " §e/party toggle <Settings> §8| §7TODO");
            config.set("info_pull_description", " §e/party help <1-3> §8| §7List aids");
            config.set("info_help_description", " §e/party list §8| §7List all party members");
            config.set("info_chat_description", " §e/party chat <message> §8| §7Send a message to the party");
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
