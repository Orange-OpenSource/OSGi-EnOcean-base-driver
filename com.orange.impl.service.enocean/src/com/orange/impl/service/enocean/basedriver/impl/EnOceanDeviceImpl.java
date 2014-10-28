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

package com.orange.impl.service.enocean.basedriver.impl;

import java.util.Map;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.device.Constants;
import org.osgi.service.enocean.EnOceanDevice;
import org.osgi.service.enocean.EnOceanException;
import org.osgi.service.enocean.EnOceanHandler;
import org.osgi.service.enocean.EnOceanMessage;
import org.osgi.service.enocean.EnOceanRPC;

import com.orange.impl.service.enocean.basedriver.EnOceanBaseDriver;
import com.orange.impl.service.enocean.basedriver.radio.MessageSYS_EX;
import com.orange.impl.service.enocean.utils.Logger;
import com.orange.impl.service.enocean.utils.Utils;

/**
 * EnOceanDeviceImpl.
 */
public class EnOceanDeviceImpl implements EnOceanDevice {

	/** TAG */
	public static final String TAG = EnOceanDeviceImpl.class.getName();
	private BundleContext bc;
	private ServiceRegistration sReg;

	private Properties props;
	private EnOceanMessage lastMessage;
	private EnOceanBaseDriver driver;
	private int chip_id;

	private int rollingCode = -1;
	private byte[] encryptionKey = null;
	private String name = null;
	private String profileName = null;
	private int securityLevelFormat = -1;

	/**
	 * An {@link EnOceanDeviceImpl} creation is directly related to its
	 * registration within the framework. Such a Device should only be
	 * registered after a proper teach-in procedure, so that the RORG, FUNC and
	 * TYPE are already known.
	 * 
	 * @param bc
	 * @param driver
	 * @param uid
	 * @param rorg
	 * @param func
	 *            the int value or -1 if no value.
	 * @param type
	 *            the int value or -1 if no value.
	 * @param manuf
	 *            the int value or -1 if no value.
	 */
	public EnOceanDeviceImpl(BundleContext bc, EnOceanBaseDriver driver,
			int uid, int rorg, int func, int type, int manuf) {
		Logger.d(TAG, "EnOceanDeviceImpl(bc: " + bc + ", driver: " + driver
				+ ", uid: " + uid + ", rorg: " + rorg + ", func: " + func
				+ ", type: " + type + ", manuf: " + manuf);
		this.bc = bc;
		this.driver = driver;
		this.chip_id = uid;
		props = new Properties();
		props.put(Constants.DEVICE_CATEGORY,
				new String[] { EnOceanDevice.DEVICE_CATEGORY });
		props.put(EnOceanDevice.CHIP_ID, String.valueOf(uid));
		props.put(EnOceanDevice.RORG, String.valueOf(rorg));
		String friendlyName = null;
		String description = null;
		if ("165".equals(String.valueOf(rorg))) {
			// hex 0xa5 == int 165.
			if ("2".equals(String.valueOf(func))) {
				if ("5".equals(String.valueOf(type))) {
					Logger.d(TAG, "This is an A5-02-05 device.");
					friendlyName = "A5-02-05";
					description = "Temperature Sensor Range 0°C to +40°C";
				} else {
					Logger.d(TAG, "This is an A5-02-yz device.");
					friendlyName = "A5-02-yz";
					description = "Not handled";
				}
			} else {
				Logger.d(TAG, "This is an A5-wx-yz device.");
				friendlyName = "A5-wx-yz";
				description = "Not handled";
			}
		} else if ("246".equals(String.valueOf(rorg))) {
			// hex 0xf6 == int 246.
			Logger.d(
					TAG,
					"This is a F6-wx-yz device. FUNC, and TYPE are NOT sent by F6-wx-yz device. The system then assumes that the device is an F6-02-01.");
			friendlyName = "F6-02-01";
			description = "Light and Blind Control - Application Style 1";
		} else if ("213".equals(String.valueOf(rorg))) {
			// hex 0xd5 == int 213.
			Logger.d(
					TAG,
					"This is a D5-wx-yz device. FUNC, and TYPE are NOT sent by D5-wx-yz device. The system then assumes that the device is a D5-00-01.");
			friendlyName = "D5-00-01";
			description = "Single Input Contact";
		} else {
			Logger.d(
					TAG,
					"This is a NOT HANDLED device (rorg: "
							+ String.valueOf(rorg)
							+ ", i.e. neither an A5-02-05 device, nor a F6-wx-yz device, nor a D5-wx-yz device. "
							+ "RORG is NOT equal to a5, nor f6,nor d5 (0xa5 is equal to int 165; 0xf6 -> 246, 0xd5 -> 213).");
		}
		props.put("DEVICE_FRIENDLY_NAME", friendlyName);
		props.put(Constants.DEVICE_DESCRIPTION, description);
		props.put(Constants.DEVICE_SERIAL, String.valueOf(uid));
		props.put("service.pid", String.valueOf(uid));
		sReg = this.bc.registerService(EnOceanDevice.class.getName(), this,
				props);
		Logger.d(
				TAG,
				"registering EnOceanDevice : "
						+ Utils.bytesToHexString(Utils.intTo4Bytes(uid)));
		/* Initializations */
		lastMessage = null;
	}

	/**
	 * @return service properties
	 */
	public Properties getServiceProperties() {
		return props;
	}

	/**
	 * @param func
	 * @param type
	 * @param manuf
	 */
	public void registerProfile(int func, int type, int manuf) {
		props.put(EnOceanDevice.FUNC, String.valueOf(func));
		props.put(EnOceanDevice.TYPE, String.valueOf(type));
		props.put(EnOceanDevice.MANUFACTURER, String.valueOf(manuf));
		sReg.setProperties(props);
	}

	public void setLearningMode(boolean learnMode) {
		// TODO Auto-generated method stub
	}

	public int getRollingCode() {
		return this.rollingCode;
	}

	public void setRollingCode(int rollingCode) {
		this.rollingCode = rollingCode;
	}

	public byte[] getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(byte[] key) {
		this.encryptionKey = key;
	}

	public int[] getLearnedDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param message
	 * @param handler
	 * @throws EnOceanException
	 */
	public void send(EnOceanMessage message, EnOceanHandler handler)
			throws EnOceanException {
		// TODO Auto-generated method stub
	}

	/**
	 * @return last message
	 */
	public EnOceanMessage getLastMessage() {
		return lastMessage;
	}

	/**
	 * @param msg
	 */
	public void setLastMessage(EnOceanMessage msg) {
		lastMessage = msg;
	}

	public Map getRPCs() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return profile name
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public int getSecurityLevelFormat() {
		return securityLevelFormat;
	}

	/**
	 * @param securityLevelFormat
	 */
	public void setSecurityLevelFormat(int securityLevelFormat) {
		this.securityLevelFormat = securityLevelFormat;
	}

	public int getRorg() {
		return getIntProperty(EnOceanDevice.RORG);
	}

	/**
	 * @param rorg
	 */
	public void setRorg(int rorg) {
		props.put(EnOceanDevice.RORG, String.valueOf(rorg));
	}

	public int getFunc() {
		return getIntProperty(EnOceanDevice.FUNC);
	}

	public void setFunc(int func) {
		props.put(EnOceanDevice.FUNC, String.valueOf(func));
		sReg.setProperties(props);
	}

	public int getType() {
		return getIntProperty(EnOceanDevice.TYPE);
	}

	public void setType(int type) {
		props.put(EnOceanDevice.TYPE, String.valueOf(type));
		sReg.setProperties(props);
	}

	public int getManufacturer() {
		return getIntProperty(EnOceanDevice.MANUFACTURER);
	}

	/**
	 * @param manuf
	 */
	public void setManufacturer(int manuf) {
		props.put(EnOceanDevice.MANUFACTURER, String.valueOf(manuf));
		sReg.setProperties(props);
	}

	public int getChipId() {
		return chip_id;
	}

	/**
	 * Safe function to get an int property
	 * 
	 * @param key
	 * @return the int-converted property, or -1
	 */
	private int getIntProperty(String key) {
		try {
			String s = (String) props.get(key);
			return Integer.parseInt(s);
		} catch (Exception e) {
			return -1;
		}
	}

	public void invoke(EnOceanRPC rpc, EnOceanHandler handler)
			throws IllegalArgumentException {
		// Generate the SYS_EX message relative to the RPC
		MessageSYS_EX msg = new MessageSYS_EX(rpc);
		for (int i = 0; i < msg.getSubTelNum(); i++) {
			byte[] telegram = (byte[]) msg.getTelegrams().get(i);
			driver.send(telegram);
		}
	}

	public void remove() {
		try {
			sReg.unregister();
		} catch (IllegalStateException e) {
			Logger.e(TAG,
					"attempt to unregister a device twice : " + e.getMessage());
		}
	}

}
