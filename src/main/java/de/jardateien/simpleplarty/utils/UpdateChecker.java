package de.jardateien.simpleplarty.utils;

import net.md_5.bungee.api.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class UpdateChecker {

	private final String CHECK_URL = "https://api.spigotmc.org/legacy/update.php?resource=%d";
	private final String RESOURCE_URL = "https://www.spigotmc.org/resources/%s.%d";

	private final Plugin plugin;
	private final int resourceId;
	private final String resourceName;

	public UpdateChecker(Plugin plugin, String name, int resourceId) {
		this.plugin = plugin;
		this.resourceId = resourceId;
		this.resourceName = name;
	}

	public void getVersion(Consumer<String> consumer) {
		var scheduler = this.plugin.getProxy().getScheduler();
		scheduler.runAsync(this.plugin, () -> {
			try (InputStream inputStream = new URL(String.format(this.CHECK_URL, this.resourceId)).openStream();
				 BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
				consumer.accept(bufferedReader.readLine());
			} catch (IOException exception) { this.plugin.getLogger().severe("Error while searching for updates: " + exception.getMessage()); }
		});
	}

	public String getURL() { return String.format(this.RESOURCE_URL, this.resourceName, this.resourceId); }
}