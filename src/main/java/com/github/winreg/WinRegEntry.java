package com.github.winreg;

import java.util.List;

/**
 *
 */
public class WinRegEntry {

	private final String path;

	private final List<WinRegEntryNode> nodes;
	private final List<WinRegEntry> entries;

	public WinRegEntry(final String path,
			final List<WinRegEntry> entries,
			final List<WinRegEntryNode> lines) {
		if (lines == null)
			throw new IllegalArgumentException();
		if (entries == null)
			throw new IllegalArgumentException();
		if (path == null)
			throw new IllegalArgumentException();

		this.path = path;
		this.nodes = lines;
		this.entries = entries;
	}

	public List<WinRegEntryNode> getNodes() {
		return nodes;
	}

	public List<WinRegEntry> getEntries() {
		return entries;
	}

}
