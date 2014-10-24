/*
 * Software Name : OSGi EnOcean base driver
 * 
 * Module name: com.orange.impl.service.enocean
 * Version: 1.0.0
 * 
 * Copyright (C) 2013 - 2014 Orange
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
 * @author Victor PERRON, Mailys ROBIN, Andre BOTTARO, Antonin CHAZALET.
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
	 * Debug
	 * 
	 * @param tag
	 * @param msg
	 */
	public static final void d(String tag, String msg) {
		if (DEBUG) {
			System.out.println("[" + tag + "] " + msg);
		}
	}

	/**
	 * Info
	 * 
	 * @param tag
	 * @param msg
	 */
	public static final void i(String tag, String msg) {
		if (INFO) {
			System.out.println("[" + tag + "] " + msg);
		}
	}

	/**
	 * Warning
	 * 
	 * @param tag
	 * @param msg
	 */
	public static final void w(String tag, String msg) {
		if (WARN) {
			System.out.println("[" + tag + "] " + msg);
		}
	}

	/**
	 * Error
	 * 
	 * @param tag
	 * @param msg
	 */
	public static final void e(String tag, String msg) {
		if (ERROR) {
			System.out.println("[" + tag + "] " + msg);
		}
	}

	/**
	 * Print
	 * 
	 * @param msg
	 */
	public static final void print(String msg) {
		System.out.println(msg);
	}
}
