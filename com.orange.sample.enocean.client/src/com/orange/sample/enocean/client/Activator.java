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

package com.orange.sample.enocean.client;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.enocean.EnOceanDevice;
import org.osgi.service.enocean.EnOceanEvent;
import org.osgi.service.enocean.EnOceanHandler;
import org.osgi.service.enocean.EnOceanMessage;
import org.osgi.service.enocean.EnOceanRPC;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.orange.sample.enocean.client.utils.Logger;
import com.orange.sample.enocean.client.utils.Utils;

/**
 * @author Victor PERRON, Mailys ROBIN, Andre BOTTARO, Antonin CHAZALET.
 */
public class Activator implements BundleActivator, ServiceTrackerCustomizer,
		EventHandler {

	private ServiceTracker deviceTracker;
	private ServiceRegistration deviceEventSubscription;
	private BundleContext bc;

	public void start(BundleContext bundleContext)
			throws InvalidSyntaxException {
		System.out
				.println("IN: com.orange.sample.enocean.client.Activator.start(bc: "
						+ bundleContext + ")");

		this.bc = bundleContext;

		/* Track device creation */
		deviceTracker = new ServiceTracker(bc,
				bc.createFilter("(&(objectclass="
						+ EnOceanDevice.class.getName() + "))"), this);
		deviceTracker.open();

		/* Track device events */
		/* Initializes self as EventHandler */
		Hashtable ht = new Hashtable();
		ht.put(org.osgi.service.event.EventConstants.EVENT_TOPIC,
				new String[] { EnOceanEvent.TOPIC_MSG_RECEIVED, });
		deviceEventSubscription = bc.registerService(
				EventHandler.class.getName(), this, ht);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ServiceReference[] srs = bc.getAllServiceReferences(
				EnOceanDevice.class.getName(), null);
		Logger.print("srs: " + srs);
		if (srs == null) {
			Logger.print("There is NO service registered with the following class name: "
					+ EnOceanDevice.class.getName());
		} else {
			Logger.print("srs.length: " + srs.length);

			int i = 0;
			while (i < srs.length) {
				ServiceReference sr = srs[i];
				Logger.print("sr: " + sr);

				String[] pks = sr.getPropertyKeys();
				int j = 0;
				while (j < pks.length) {
					Logger.print("pks[" + j + "]: " + pks[j]
							+ ", event.getProperty(" + pks[j] + "): "
							+ sr.getProperty(pks[j]));
					j = j + 1;
				}

				EnOceanDevice eod = (EnOceanDevice) bc.getService(sr);
				Logger.print("eod: " + eod);
				Logger.print("eod.getChipId(): " + eod.getChipId());
				Logger.print("eod.getFunc(): " + eod.getFunc());
				Logger.print("eod.getManufacturer(): " + eod.getManufacturer());
				Logger.print("eod.getRollingCode(): " + eod.getRollingCode());
				Logger.print("eod.getRorg(): " + eod.getRorg());
				Logger.print("eod.getSecurityLevelFormat(): "
						+ eod.getSecurityLevelFormat());
				Logger.print("eod.getType(): " + eod.getType());
				Logger.print("eod.getClass(): " + eod.getClass());
				Logger.print("eod.getEncryptionKey(): "
						+ eod.getEncryptionKey());
				Logger.print("eod.getLearnedDevices(): "
						+ eod.getLearnedDevices());
				Logger.print("eod.getRPCs(): " + eod.getRPCs());

				// The following RPC is a copy of:
				// org.osgi.test.cases.enocean.rpc.QueryFunction
				EnOceanRPC rpc = new EnOceanRPC() {

					// sender='0x0180abb8'

					// propertyNames[0]: enocean.device.profile.func,
					// event.getProperty(propertyNames[0]): -1

					// propertyNames[1]: enocean.device.profile.rorg,
					// event.getProperty(propertyNames[1]): 165

					// propertyNames[2]: enocean.device.chip_id,
					// event.getProperty(propertyNames[2]): 25209784

					// propertyNames[3]: enocean.device.profile.type,
					// event.getProperty(propertyNames[3]): -1

					// propertyNames[4]: enocean.message,
					// event.getProperty(propertyNames[4]): a5000074080180abb800

					// private int senderId = -1;
					private int senderId = 0x0180abb8;

					public void setSenderId(int chipId) {
						this.senderId = chipId;
					}

					public void setPayload(byte[] data) {
						// does nothing;
						Logger.print("rpc.setPayLoad(data: " + data + ")");
					}

					public int getSenderId() {
						return senderId;
					}

					public byte[] getPayload() {
						return null;
					}

					public int getManufacturerId() {
						return 0x07ff;
					}

					public int getFunctionId() {
						// return 0x0007;
						return -1;
					}
				};

				EnOceanHandler handler = new EnOceanHandler() {
					public void notifyResponse(EnOceanRPC enOceanRPC,
							byte[] payload) {
						Logger.print("enOceanRPC: " + enOceanRPC
								+ ", payload: " + payload);
					}
				};

				Logger.print("BEFORE invoking...");
				eod.invoke(rpc, handler);
				Logger.print("AFTER invoking...");

				i = i + 1;
			}
		}

		System.out
				.println("OUT: com.orange.sample.enocean.client.Activator.start(bc: "
						+ bc + ")");
	}

	public void stop(BundleContext bundleContext) {
		deviceTracker.close();
		deviceEventSubscription.unregister();
	}

	public Object addingService(ServiceReference reference) {
		Logger.print("> addingService(reference: " + reference + ")");
		Object service = bc.getService(reference);
		if (service != null) {
			if (service instanceof EnOceanDevice) {
				EnOceanDevice device = (EnOceanDevice) service;
				Logger.print("> Registered a new EnOceanDevice : "
						+ Utils.printUid(device.getChipId()) + ", device: "
						+ device);
				return service;
			}
		}
		return null;
	}

	public void modifiedService(ServiceReference reference, Object service) {
		Logger.print("> modifiedService(reference: " + reference
				+ ", service: " + service + ")");
		if (service != null) {
			if (service instanceof EnOceanDevice) {
				EnOceanDevice device = (EnOceanDevice) service;
				Logger.print("> modifiedService method. device.getChipId(): "
						+ Utils.printUid(device.getChipId()) + ", device: "
						+ device);
			}
		}
	}

	public void removedService(ServiceReference reference, Object service) {
		Logger.print("> removedService(reference: " + reference + ", service: "
				+ service + ")");
		if (service != null) {
			if (service instanceof EnOceanDevice) {
				EnOceanDevice device = (EnOceanDevice) service;
				Logger.print("> removedService method. device.getChipId(): "
						+ Utils.printUid(device.getChipId()) + ", device: "
						+ device);
			}
		}
	}

	public void handleEvent(Event event) {
		Logger.print("> handleEvent(event: " + event);

		Logger.print("event.getTopic(): " + event.getTopic());
		// event.getTopic():
		// org/osgi/service/enocean/EnOceanEvent/MESSAGE_RECEIVED
		Logger.print("event.getPropertyNames(): " + event.getPropertyNames());
		// event.getPropertyNames(): [Ljava.lang.String;@194737d

		String[] pns = event.getPropertyNames();
		int i = 0;
		while (i < pns.length) {
			Logger.print("pns[" + i + "]: " + pns[i] + ", event.getProperty("
					+ pns[i] + "): " + event.getProperty(pns[i]));
			i = i + 1;

			// pns[0]: enocean.device.profile.func,
			// event.getProperty(enocean.device.profile.func): 2
			// pns[1]: enocean.device.profile.rorg,
			// event.getProperty(enocean.device.profile.rorg): 165
			// pns[2]: enocean.device.chip_id,
			// event.getProperty(enocean.device.chip_id): 8969457
			// pns[3]: enocean.device.profile.type,
			// event.getProperty(enocean.device.profile.type): 5
			// pns[4]: enocean.message, event.getProperty(enocean.message):
			// a5000035080088dcf100
			// pns[5]: event.topics, event.getProperty(event.topics):
			// org/osgi/service/enocean/EnOceanEvent/MESSAGE_RECEIVED
		}

		String topic = event.getTopic();
		if (topic.equals(EnOceanEvent.TOPIC_MSG_RECEIVED)) {
			String chipId = (String) event.getProperty(EnOceanDevice.CHIP_ID);
			String rorg = (String) event.getProperty(EnOceanDevice.RORG);
			String func = (String) event.getProperty(EnOceanDevice.FUNC);
			String type = (String) event.getProperty(EnOceanDevice.TYPE);
			EnOceanMessage data = (EnOceanMessage) event
					.getProperty(EnOceanEvent.PROPERTY_MESSAGE);
			String displayId = Utils.printUid(Integer.parseInt(chipId));
			String profile = rorg + "/" + func + "/" + type;
			Logger.print("> MSG_RECEIVED event : sender=" + displayId
					+ ", profile = '" + profile + "'");
			Logger.print("Try to identify the device that has sent the just received event (e.g. is it an A5-02-05 device - a temperature sensor range 0°C to +40°C ?).");
			if ("165".equals(rorg)) {
				// hex 0xa5 == int 165.
				if ("2".equals(func)) {
					if ("5".equals(type)) {
						Logger.print("This event has been sent by an A5-02-05 device.");
						Logger.print("The end of page 12, and the beginning of page 13 of EnOcean_Equipment_Profiles_EEP_V2.61_public.pdf specifies how to get the temp°C value starting from an EnOcean telegram.");
						// propertyNames[4]: enocean.message,
						// event.getProperty(propertyNames[4]):
						// a5000035080088dcf100
						byte[] payload = data.getPayloadBytes();
						Logger.print("payload: " + payload
								+ ", payload.length: " + payload.length);
						int j = 0;
						while (j < payload.length) {
							Logger.print("payload[" + j + "]: " + payload[j]);
							j = j + 1;
						}
						byte rawTemperatureDB1InHexAsAByte = payload[2];
						float rawTemperatureDB1InNumberAsADouble = rawTemperatureDB1InHexAsAByte;
						Logger.print("rawTemperatureDB1InNumberAsADouble: "
								+ rawTemperatureDB1InNumberAsADouble);
						if (rawTemperatureDB1InNumberAsADouble < 0) {
							Logger.print("rawTemperatureDB1InNumberAsADouble is negative, so let's convert rawTemperatureDB1InNumberAsADouble to unsigned 0..255 value instead of -127..128 one.");
							rawTemperatureDB1InNumberAsADouble = rawTemperatureDB1InNumberAsADouble
									* -1
									+ 2
									* (128 + rawTemperatureDB1InNumberAsADouble);
							Logger.print("rawTemperatureDB1InNumberAsADouble: "
									+ rawTemperatureDB1InNumberAsADouble);
						} else {
							Logger.print("rawTemperatureDB1InNumberAsADouble is positive, everything is ok.");
						}

						// Now let's apply the formula:
						// (rawTemperatureDB1InNumberAsADouble-255)*-40/255+0 =
						// temp in celsius.
						double tempInCelsius = (rawTemperatureDB1InNumberAsADouble - 255)
								* -40 / 255 + 0;
						Logger.print("tempInCelsius: " + tempInCelsius);
					} else {
						Logger.print("This event has NOT been sent by an A5-02-05 device. TYPE is NOT equal to 5.");
					}
				} else {
					Logger.print("This event has NOT been sent by an A5-02-05 device. FUNC is NOT equal to 2.");
				}
			} else if ("246".equals(rorg)) {
				// hex 0xf6 == int 246.
				Logger.print("This event has been sent by an F6-wx-yz device.");
				Logger.print("FUNC, and TYPE are NOT sent by F6-wx-yz device. The system then assumes that the device is an F6-02-01.");
				Logger.print("In EnOcean_Equipment_Profiles_EEP_V2.61_public.pdf, pages 13-14, F6-02-01 -> RPS Telegram, Rocker Switch, 2 Rocker, Light and Blind Control - Application Style 1");

				// byte[] payload = data.getPayloadBytes(); using
				// getPayloadBytes() is NOT enough here.
				byte[] payload = data.getBytes();
				// e.g. f6500029219f3003ffffffff3100 when the button BI of an
				// F6-02-01 device is pressed.

				Logger.print("payload: " + payload + ", payload.length: "
						+ payload.length);
				int j = 0;
				while (j < payload.length) {
					Logger.print("payload["
							+ j
							+ "] & 0xff (value is displayed as an unsigned int): "
							+ (payload[j] & 0xff));
					j = j + 1;
				}

				byte dataDB0InHexAsAByte = payload[1];
				Logger.print("dataDB0InHexAsAByte: " + dataDB0InHexAsAByte);
				int dataDB0InHexAsAnInt = dataDB0InHexAsAByte & 0xff;
				Logger.print("dataDB0InHexAsAnInt: " + dataDB0InHexAsAnInt);

				byte statusInHexAsAByte = payload[6];
				Logger.print("statusInHexAsAByte: " + statusInHexAsAByte);
				int statusInHexAsAsAnInt = statusInHexAsAByte & 0xff;
				Logger.print("statusInHexAsAsAnInt: " + statusInHexAsAsAnInt);

				if ((new Integer(0x30).byteValue() & 0xff) == statusInHexAsAsAnInt) {
					Logger.print("Here, a button has been pressed.");
					if ((new Integer(0x30).byteValue() & 0xff) == dataDB0InHexAsAnInt) {
						// Check if A0 button has been pressed --> 0x30
						Logger.print("A0");
					} else if ((new Integer(0x10).byteValue() & 0xff) == dataDB0InHexAsAnInt) {
						// Check if AI button has been pressed --> 0x10
						Logger.print("AI");
					} else if ((new Integer(0x70).byteValue() & 0xff) == dataDB0InHexAsAnInt) {
						// Check if A0 button has been pressed --> 0x70
						Logger.print("B0");
					} else if ((new Integer(0x50).byteValue() & 0xff) == dataDB0InHexAsAnInt) {
						// Check if A0 button has been pressed --> 0x50
						Logger.print("BI");
					} else {
						Logger.print("The given Data DB_0 is UNKNOWN; its value is: "
								+ dataDB0InHexAsAnInt);
					}
				} else if ((new Integer(0x20).byteValue() & 0xff) == statusInHexAsAsAnInt) {
					Logger.print("Here, a button has been released (normally, this button was the pressed one.)");
				} else {
					Logger.print("The given status field of this RPS telegram is UNKNOWN. This status was (as an int): "
							+ statusInHexAsAsAnInt);
				}
			} else if ("213".equals(rorg)) {
				// hex 0xd5 == int 213.
				Logger.print("This event has been sent by a D5-wx-yz device.");
				Logger.print("FUNC, and TYPE are NOT sent by D5-wx-yz device. The system then assumes that the device is an D5-00-01.");
				Logger.print("In EnOcean_Equipment_Profiles_EEP_V2.61_public.pdf, pages 24, D5-00-01 -> 1BS Telegram, Contacts and Switches, Single Input Contact.");

				// Logger.print("data.getBytes(): " + data.getBytes());
				// Logger.print("Utils.bytesToHexString(data.getBytes()): "
				// + Utils.bytesToHexString(data.getBytes()));
				//
				// Logger.print("data.getDbm(): " + data.getDbm());
				// Logger.print("data.getDestinationId(): "
				// + data.getDestinationId());
				// Logger.print("data.getFunc(): " + data.getFunc());
				//
				// Logger.print("data.getPayloadBytes(): "
				// + data.getPayloadBytes());
				// Logger.print("Utils.bytesToHexString(data.getPayloadBytes()): "
				// + Utils.bytesToHexString(data.getPayloadBytes()));
				//
				// Logger.print("data.getRorg(): " + data.getRorg());
				// Logger.print("data.getSecurityLevelFormat(): "
				// + data.getSecurityLevelFormat());
				// Logger.print("data.getSenderId(): " + data.getSenderId());
				// Logger.print("data.getStatus(): " + data.getStatus());
				// Logger.print("data.getSubTelNum(): " + data.getSubTelNum());
				// Logger.print("data.getType(): " + data.getType());

				if (8 == data.getPayloadBytes()[0]) {
					Logger.print("An opening has been detected.");
				} else if (9 == data.getPayloadBytes()[0]) {
					Logger.print("A closing has been detected.");
				} else if (0 == data.getPayloadBytes()[0]) {
					Logger.print("The LRN button has been pressed.");
				} else {
					Logger.print("The given 1BS's Data DB_0 value (data.getPayloadBytes()[0]: "
							+ data.getPayloadBytes()[0]
							+ " doesn't correspond to anything in EnOcean's specs. There is a pb. The system doesn't know how to handle this message.");
				}
			} else {
				Logger.print("This event has NOT been sent by an A5-02-05 device, nor by a F6-wx-yz device, nor by a D5-wx-yz device. "
						+ "RORG is NOT equal to a5, nor f6,nor d5 (0xa5 is equal to int 165; 0xf6 -> 246, 0xd5 -> 213).");
			}
		}
	}
}
