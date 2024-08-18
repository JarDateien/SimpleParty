package de.jardateien.simpleparty.utils;

import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class UpdateChecker {

	private final String CHECK_URL = "https://api.spigotmc.org/legacy/update.php?resource=%d";

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

	public String getURL() {
		String RESOURCE_URL = "https://www.spigotmc.org/resources/%s.%d";
		return String.format(RESOURCE_URL, this.resourceName, this.resourceId); }

	public void update() {
		try {
			String DOWNLOAD_URL = "https://api.spiget.org/v2/resources/%d/download";
			var downloadURL = new URL(String.format(DOWNLOAD_URL, this.resourceId));
			BufferedInputStream inputStream = null;
			FileOutputStream outputStream = null;
			try {
				this.plugin.getLogger().info("Trying to download:");
				inputStream = new BufferedInputStream(downloadURL.openStream());
				String FILE_PATH = "plugins/%s.jar";
				outputStream = new FileOutputStream(String.format(FILE_PATH, this.plugin.getDescription().getName()));
				byte[] data = new byte[1024];
				int count;
				while ((count = inputStream.read(data, 0, 1024)) != -1) outputStream.write(data, 0, count);
			} finally {
				if (inputStream != null) inputStream.close();
				if (outputStream != null) outputStream.close();
			}

			this.plugin.getLogger().info("Succesfully downloaded");
			this.plugin.getLogger().info("To install the new features you have to restart your server!");

		} catch (IOException e) { this.plugin.getLogger().warning("Unable to download update!"); }
	}
}