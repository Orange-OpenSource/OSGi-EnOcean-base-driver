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

package com.orange.impl.service.enocean.utils;

/**
 * PortInUseException.
 */
public class PortInUseException extends Exception {
	/** generated */
	private static final long serialVersionUID = -8943051586981003647L;
	/**
	 * 
	 */
	public String currentOwner;

	PortInUseException(String paramString) {
		super(paramString);
		this.currentOwner = paramString;
	}

	/**
	 * 
	 */
	public PortInUseException() {

	}
}
