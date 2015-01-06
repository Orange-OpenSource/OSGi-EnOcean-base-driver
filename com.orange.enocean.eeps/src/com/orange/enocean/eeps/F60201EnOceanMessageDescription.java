package com.orange.enocean.eeps;

import org.osgi.service.enocean.EnOceanChannel;
import org.osgi.service.enocean.descriptions.EnOceanMessageDescription;

/**
 * EnOcean F6-02-01 EEP EnOceanMessageDescription.
 */
public class F60201EnOceanMessageDescription implements
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
		String description = "Light and Blind Control - Application Style 1";
		return description;
	}

}
