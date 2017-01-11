package com.wfghc.cadastrodepessoa.util;

public final class WrapperLog {

	public static final boolean LOG_ENABLE = true;

	public static final String LOG_TAG = "MVP";

	private WrapperLog() {
		super();
	}

	/**
	 * Method log the msg received
	 * 
	 * @param msg
	 */
	public static void info(final String msg) {

		if (LOG_ENABLE) {
			System.out.println(LOG_TAG + " - " + msg);
		}
	}
}
