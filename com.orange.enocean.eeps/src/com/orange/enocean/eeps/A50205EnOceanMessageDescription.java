package com.orange.enocean.eeps;

import org.osgi.service.enocean.EnOceanChannel;
import org.osgi.service.enocean.descriptions.EnOceanMessageDescription;

/**
 * EnOcean A5-02-05 EEP EnOceanMessageDescription.
 */
public class A50205EnOceanMessageDescription implements
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
		String description = "Temperature Sensor Range 0°C to +40°C";
		return description;
	}

}
