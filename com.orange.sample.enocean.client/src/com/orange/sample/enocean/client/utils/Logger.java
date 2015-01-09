/*
 * Software Name : OSGi EnOcean base driver
 * 
 * Module name: com.orange.impl.service.enocean
 * Version: 1.0.0
 * 
 * Copyright (C) 2013 - 2015 Orange
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author(s): Victor PERRON, Mailys ROBIN, Andre BOTTARO, Antonin CHAZALET.
 */

package com.orange.sample.enocean.client.utils;

/**
 * Logger.
 */
public final class Logger {

	static final boolean DEBUG = Boolean
			.getBoolean("org.osgi.service.enocean.loglevel.debug");
	static final boolean WARN = Boolean
			.getBoolean("org.osgi.service.enocean.loglevel.warn");
	static final boolean INFO = Boolean
			.getBoolean("org.osgi.service.enocean.loglevel.info");
	static final boolean ERROR = Boolean
			.getBoolean("org.osgi.service.enocean.loglevel.error");

	/**
	 * DEBUG
	 * 
	 * @param tag
	 * @param msg
	 */
	public static final void d(String tag, String msg) {
		if (DEBUG) {
			print("[DEBUG-" + tag + "] " + msg);
		}
	}

	/**
	 * INFO
	 * 
	 * @param tag
	 * @param msg
	 */
	public static final void i(String tag, String msg) {
		if (INFO) {
			print("[INFO--" + tag + "] " + msg);
		}
	}

	/**
	 * WARN
	 * 
	 * @param tag
	 * @param msg
	 */
	public static final void w(String tag, String msg) {
		if (WARN) {
			print("[WARN--" + tag + "] " + msg);
		}
	}

	/**
	 * ERROR
	 * 
	 * @param tag
	 * @param msg
	 */
	public static final void e(String tag, String msg) {
		if (ERROR) {
			print("[ERROR-" + tag + "] " + msg);
		}
	}

	/**
	 * Print
	 * 
	 * @param msg
	 */
	private static final void print(String msg) {
		System.out.println(msg);
	}
}
