package com.github.winreg;

/**
 *
 */
public class WinRegEntryNode {

	private final WinRegistryType type;
	private final String value;
	private final String name;

	public WinRegEntryNode(final String name, final WinRegistryType type, final String value) {
		if (type == null)
			throw new IllegalArgumentException();
		if (name == null)
			throw new IllegalArgumentException();
		if (value == null)
			throw new IllegalArgumentException();
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public WinRegistryType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
