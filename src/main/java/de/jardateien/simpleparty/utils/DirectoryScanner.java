package de.jardateien.simpleparty.utils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;

public class DirectoryScanner {

	private final File directory;
	private  FileFilter filter;

	public DirectoryScanner(@Nonnull File directory) {
		this.directory = directory;
		this.filter = file -> true;
	}

	public DirectoryScanner withFilter(FileFilter filter) {
		this.filter = filter;
		return this;
	}

	public void scanForFiles(Consumer<File> callback) {
		if (!this.directory.exists()) this.directory.mkdir();
		if (!this.directory.isDirectory()) throw new IllegalArgumentException("File must be a directory.");
		for (File file : this.directory.listFiles(this.filter)) { callback.accept(file); }
	}
}