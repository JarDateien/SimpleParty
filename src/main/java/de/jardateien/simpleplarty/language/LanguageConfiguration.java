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

    public LanguageConfiguration(final File folder) {
        super();
        this.folder = new File(folder + "/Languages");
        this.languages = new ArrayList<>();
        this.loadConfiguration(this.folder);
    }

    private void loadConfiguration(File folder) {
        if(!folder.exists()) folder.mkdirs();
        var scanner = new DirectoryScanner(this.folder).withFilter(file -> file.getName().contains(".yml") && !file.getName().startsWith("-"));
        scanner.scanForFiles(languages -> this.languages.add(new Language(languages, this.configuration)));
    }

    public void save() {
        for (Language language : this.languages) {
            try { this.configuration.save(language.getConfiguration(), language.getFile()); }
            catch (IOException exception) { exception.printStackTrace(); }
        }
    }
}
