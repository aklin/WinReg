package com.github.winreg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

public class WinRegistry {

	public static final String HKCU = "HKEY_CURRENT_USER";
	public static final String HKCR = "HKEY_CLASSES_ROOT";
	public static final String HKLM = "HKEY_LOCAL_MACHINE";
	public static final String HKCC = "HKEY_CURRENT_CONFIG";

	private final ByteArrayOutputStream out;
	private int timeoutms;

	public WinRegistry() {
		timeoutms = 3000;
		out = new ByteArrayOutputStream();
	}

	/**
	 * Run a raw Registry query. If the REG output is ERROR, an empty list will be
	 * returned regardless of other output.
	 * @param query
	 * @param separator Results separator. Use null for default '\0'
	 * @param types Can be null.
	 * @param flags Can be null.
	 * @return
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 */
	public List<String> raw(final String query,
			final String separator,
			final List<WinRegistryType> types,
			final WinRegistryFlags... flags) throws IOException, InterruptedException {
		final DefaultExecutor exe;
		final DefaultExecuteResultHandler handler;
		final PumpStreamHandler stream;
		final CommandLine com;

		if (query == null)
			throw new IllegalArgumentException("Query string cannot be null");

		if (query.trim().equals("")) //don't bother
			return Collections.EMPTY_LIST;

		out.reset();

		com = new CommandLine("reg")
				.addArgument(query, true);

		if (types != null) {
			com.addArgument("/t");
			for (WinRegistryType t : types)
				com.addArgument(t.toString());
		}

		if (flags != null)
			for (WinRegistryFlags a : flags)
				com.addArgument(a.toString());

		if (separator != null)
			com.addArgument("/se")
					.addArgument(separator);

		exe = new DefaultExecutor();
		stream = new PumpStreamHandler(out);
		handler = new DefaultExecuteResultHandler();

		exe.setWatchdog(new ExecuteWatchdog(5000)); //kill in 5 seconds
		exe.setStreamHandler(stream);

		exe.execute(com, handler);
		handler.waitFor();

		return prepareOutput();
	}

	public List<String> query(final String key) {
		final StringBuilder b;

		b = new StringBuilder();

		return null;

	}

	/**
	 * Returns all text strings as a List. Trims output and omits empty lines. Will return
	 * null on parse error.
	 * @return null on error, Collections.EMPTY_LIST if only non-empty lines were returned
	 * @return The list of non-empty strings outputted by the external command
	 */
	private List<String> prepareOutput() {
		final ArrayList<String> list;
		final Scanner sc;

		sc = new Scanner(out.toString());
		list = new ArrayList<>();

		while (sc.hasNextLine()) {
			final String l = sc.nextLine().trim();

			if (l.isEmpty())
				continue;
			if (l.startsWith("ERROR")) { //oops
				System.err.println("ERROR! Returning empty list");
				return null;
			}

			list.add(l);
		}

		return list.isEmpty() ? Collections.EMPTY_LIST : list;
	}

	/**
	 * Get command timeout. Default value is 3000 (3 seconds).
	 * @return
	 */
	public int getTimeoutMS() {
		return timeoutms;
	}

	/**
	 * Set timeout in milliseconds.
	 * @param ms Non-negative timeout (milliseconds).
	 * @return This object
	 */
	public WinRegistry setTimeoutMS(int ms) {
		if (ms < 0)
			throw new IllegalArgumentException("Timeout value must be non-negative");
		timeoutms = ms;
		return this;
	}
}
