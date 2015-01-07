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

package com.orange.enocean.eeps;

import org.osgi.service.enocean.EnOceanChannel;
import org.osgi.service.enocean.descriptions.EnOceanMessageDescription;

/**
 * EnOcean D5-00-01 EEP EnOceanMessageDescription.
 */
public class D50001EnOceanMessageDescription implements
		EnOceanMessageDescription {

	public byte[] serialize(EnOceanChannel[] channels)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public EnOceanChannel[] deserialize(byte[] bytes)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMessageDescription() {
		// default description
		String description = "Single Input Contact";
		return description;
	}

}
