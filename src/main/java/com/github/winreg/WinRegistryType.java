package com.github.winreg;

/**
 *
 */
public enum WinRegistryType {

	REG_SZ("REG_SZ"),
	REG_MULTI_SZ("REG_MULTI_SZ"),
	REG_EXPAND_SZ("REG_EXPAND_SZ"),
	REG_DWORD("REG_DWORD"),
	REG_BINARY("REG_BINARY"),
	REG_NONE("REG_NONE");

	private final String type;

	private WinRegistryType(final String t) {
		type = t;
	}

	@Override
	public String toString() {
		return type;
	}
}
