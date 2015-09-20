package com.github.winreg;

/**
 *
 */
public enum WinRegistryFlags {

	RECURSIVE("/s"),
	CASE_SENSITIVE("/c"),
	EXACT_ONLY("/e"),
	EMPTY_ONLY("/ve"),
	KEYS_ONLY("/k"),
	DATA_ONLY("/d"),
	NUMERIC_TYPE("/z");

	private final String arg;

	private WinRegistryFlags(final String s) {
		arg = s;
	}

	@Override
	public String toString() {
		return arg;
	}
}
