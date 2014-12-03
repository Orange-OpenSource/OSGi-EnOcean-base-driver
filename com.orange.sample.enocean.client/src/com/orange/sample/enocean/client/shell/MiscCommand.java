package com.orange.sample.enocean.client.shell;

import java.io.IOException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.enocean.EnOceanDevice;
import org.osgi.service.enocean.EnOceanHandler;
import org.osgi.service.enocean.EnOceanRPC;

import com.orange.sample.enocean.client.utils.Logger;

/**
 *
 */
public class MiscCommand {

	private final BundleContext bc;

	/**
	 * @param bc
	 */
	public MiscCommand(BundleContext bc) {
		this.bc = bc;
	}

	/**
	 * 
	 */
	public void aa() {
		String result = "aa command";
		System.out.println(result);
	}

	/**
	 * @param param1
	 * @return result
	 * @throws IOException
	 */
	public String bb(String param1) throws IOException {
		String result = "bb command with param1: " + param1;
		// System.out.println(result);
		return result;
	}

	/**
	 * display all the services.
	 */
	public void ds() {
		System.out.println("This ds command displays all the services.");
		// display all the services.
		try {
			ServiceReference[] allsrs = bc.getAllServiceReferences(null, null);
			Logger.print("allsrs: " + allsrs);
			if (allsrs == null) {
				Logger.print("There is NO service registered at all.");
			} else {
				Logger.print("allsrs.length: " + allsrs.length);

				int i = 0;
				while (i < allsrs.length) {
					ServiceReference sr = allsrs[i];
					Logger.print("sr: " + sr);
					String[] pks = sr.getPropertyKeys();
					int j = 0;
					while (j < pks.length) {
						Logger.print("pks[" + j + "]: " + pks[j]
								+ ", sr.getProperty(" + pks[j] + "): "
								+ sr.getProperty(pks[j]));
						Logger.print("sr.getProperty(" + pks[j]
								+ ").getClass().getName()"
								+ sr.getProperty(pks[j]).getClass().getName());
						j = j + 1;
					}
					i = i + 1;
				}
			}
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * send a EnOcean packet in order to appair with the plug.
	 */
	public void ap() {
		// 55 00 0D 07 01 FD D4 D2 01 08 00 3E FF 91 00 00 00 00 00 03 01 85 64
		// 14 FF 00 E9

		// Let's create an enoceanrpc in order to appair with the plug.
		EnOceanRPC appairRPC = new EnOceanRPC() {
			private int senderId = 0x00000000;

			public void setSenderId(int chipId) {
				this.senderId = chipId;
			}

			public int getSenderId() {
				return senderId;
			}

			public byte[] getPayload() {
				return null;
			}

			public int getManufacturerId() {
				return -1;
			}

			public int getFunctionId() {
				return -1;
			}

			public String getName() {
				return "HARDCODED_UTE_APPAIR";
			}
		};

		EnOceanHandler handlerTurnOnRpc = new EnOceanHandler() {
			public void notifyResponse(EnOceanRPC enOceanRPC, byte[] payload) {
				Logger.print("enOceanRPC: " + enOceanRPC + ", payload: "
						+ payload);
			}
		};

		EnOceanDevice eod = getAnEnOceanDevice();
		if (eod == null) {
			Logger.e(this.getClass().getName(),
					"There is no EnOceanDevice --> So no invocation is possible.");
		} else {
			Logger.e(this.getClass().getName(),
					"There is an EnOceanDevice --> So invocation is possible.");
			Logger.print("BEFORE invoking...");
			eod.invoke(appairRPC, handlerTurnOnRpc);
			Logger.print("AFTER invoking...");
		}
	}

	/**
	 * send a EnOcean packet in order to turn on the plug.
	 */
	public void on() {
		// 55 00 09 07 01 56 D2 01 00 01 00 00 00 00 00 03 01 85 64 14 FF 00 64

		// Let's create an enoceanrpc in order to appair with the plug.
		EnOceanRPC appairRPC = new EnOceanRPC() {
			private int senderId = 0x00000000;

			public void setSenderId(int chipId) {
				this.senderId = chipId;
			}

			public int getSenderId() {
				return senderId;
			}

			public byte[] getPayload() {
				return null;
			}

			public int getManufacturerId() {
				return -1;
			}

			public int getFunctionId() {
				return -1;
			}

			public String getName() {
				return "HARDCODED_VLD_TURN_ON";
			}
		};

		EnOceanHandler handlerTurnOnRpc = new EnOceanHandler() {
			public void notifyResponse(EnOceanRPC enOceanRPC, byte[] payload) {
				Logger.print("enOceanRPC: " + enOceanRPC + ", payload: "
						+ payload);
			}
		};

		EnOceanDevice eod = getAnEnOceanDevice();
		if (eod == null) {
			Logger.e(this.getClass().getName(),
					"There is no EnOceanDevice --> So no invocation is possible.");
		} else {
			Logger.e(this.getClass().getName(),
					"There is an EnOceanDevice --> So invocation is possible.");
			Logger.print("BEFORE invoking...");
			eod.invoke(appairRPC, handlerTurnOnRpc);
			Logger.print("AFTER invoking...");
		}

	}

	/**
	 * send a EnOcean packet in order to turn off the plug.
	 */
	public void of() {
		// 55 00 09 07 01 56 D2 01 00 00 00 00 00 00 00 03 01 85 64 14 FF 00 F0

		// Let's create an enoceanrpc in order to appair with the plug.
		EnOceanRPC appairRPC = new EnOceanRPC() {
			private int senderId = 0x00000000;

			public void setSenderId(int chipId) {
				this.senderId = chipId;
			}

			public int getSenderId() {
				return senderId;
			}

			public byte[] getPayload() {
				return null;
			}

			public int getManufacturerId() {
				return -1;
			}

			public int getFunctionId() {
				return -1;
			}

			public String getName() {
				return "HARDCODED_VLD_TURN_OFF";
			}
		};

		EnOceanHandler handlerTurnOnRpc = new EnOceanHandler() {
			public void notifyResponse(EnOceanRPC enOceanRPC, byte[] payload) {
				Logger.print("enOceanRPC: " + enOceanRPC + ", payload: "
						+ payload);
			}
		};

		EnOceanDevice eod = getAnEnOceanDevice();
		if (eod == null) {
			Logger.e(this.getClass().getName(),
					"There is no EnOceanDevice --> So no invocation is possible.");
		} else {
			Logger.e(this.getClass().getName(),
					"There is an EnOceanDevice --> So invocation is possible.");
			Logger.print("BEFORE invoking...");
			eod.invoke(appairRPC, handlerTurnOnRpc);
			Logger.print("AFTER invoking...");
		}

	}

	/**
	 * send a EnOcean packet as PTM210 in order to appair with the plug, or
	 * WaterControl.
	 */
	public void apb() {
		// 55 00 07 07 01 7A F6 50 00 29 21 9F 30 01 FF FF FF FF 31 00 37

		// Let's create an enoceanrpc in order to appair with the plug.
		EnOceanRPC appairRPC = new EnOceanRPC() {
			private int senderId = 0x00000000;

			public void setSenderId(int chipId) {
				this.senderId = chipId;
			}

			public int getSenderId() {
				return senderId;
			}

			public byte[] getPayload() {
				return null;
			}

			public int getManufacturerId() {
				return -1;
			}

			public int getFunctionId() {
				return -1;
			}

			public String getName() {
				return "HARDCODED_APPAIR_TURN_ON";
			}
		};

		EnOceanHandler handlerTurnOnRpc = new EnOceanHandler() {
			public void notifyResponse(EnOceanRPC enOceanRPC, byte[] payload) {
				Logger.print("enOceanRPC: " + enOceanRPC + ", payload: "
						+ payload);
			}
		};

		EnOceanDevice eod = getAnEnOceanDevice();
		if (eod == null) {
			Logger.e(this.getClass().getName(),
					"There is no EnOceanDevice --> So no invocation is possible.");
		} else {
			Logger.e(this.getClass().getName(),
					"There is an EnOceanDevice --> So invocation is possible.");
			Logger.print("BEFORE invoking...");
			eod.invoke(appairRPC, handlerTurnOnRpc);
			Logger.print("AFTER invoking...");
		}
	}

	/**
	 * send a EnOcean packet as PTM210 in order to turn on the plug, or
	 * WaterControl.
	 */
	public void onb() {
		apb();
	}

	/**
	 * send a EnOcean packet as PTM210 in order to turn off the plug, or
	 * WaterControl.
	 */
	public void ofb() {
		// 55 00 07 07 01 7A F6 70 00 29 21 9F 30 01 FF FF FF FF 2D 00 62

		// Let's create an enoceanrpc in order to appair with the plug.
		EnOceanRPC appairRPC = new EnOceanRPC() {
			private int senderId = 0x00000000;

			public void setSenderId(int chipId) {
				this.senderId = chipId;
			}

			public int getSenderId() {
				return senderId;
			}

			public byte[] getPayload() {
				return null;
			}

			public int getManufacturerId() {
				return -1;
			}

			public int getFunctionId() {
				return -1;
			}

			public String getName() {
				return "HARDCODED_TURN_OFF";
			}
		};

		EnOceanHandler handlerTurnOnRpc = new EnOceanHandler() {
			public void notifyResponse(EnOceanRPC enOceanRPC, byte[] payload) {
				Logger.print("enOceanRPC: " + enOceanRPC + ", payload: "
						+ payload);
			}
		};

		EnOceanDevice eod = getAnEnOceanDevice();
		if (eod == null) {
			Logger.e(this.getClass().getName(),
					"There is no EnOceanDevice --> So no invocation is possible.");
		} else {
			Logger.e(this.getClass().getName(),
					"There is an EnOceanDevice --> So invocation is possible.");
			Logger.print("BEFORE invoking...");
			eod.invoke(appairRPC, handlerTurnOnRpc);
			Logger.print("AFTER invoking...");
		}

	}

	/**
	 * @return an EnOceanDevice if any, the system doesn't care which on it will
	 *         be. Return null otherwise.
	 */
	private EnOceanDevice getAnEnOceanDevice() {
		try {
			ServiceReference[] srs = bc.getAllServiceReferences(
					EnOceanDevice.class.getName(), null);
			Logger.print("srs: " + srs);
			if (srs == null) {
				Logger.print("There is NO service registered with the following class name: "
						+ EnOceanDevice.class.getName());
				return null;
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
					return eod;
				}
				return null;
			}
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}
