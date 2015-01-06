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
