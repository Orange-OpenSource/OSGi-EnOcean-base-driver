package com.orange.impl.service.enocean.basedriver.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.orange.impl.service.enocean.utils.Utils;

/**
 * Manage the configuration file.
 */
public class ConfigurationFileManager {

	private static Properties config = new Properties();

	/**
	 * Tests.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("---"
				+ getRorgFuncTypeAndFriendlynameFromConfigFile("0x12345678"));

		System.out.println("---"
				+ getRorgFuncTypeAndFriendlynameFromConfigFile(null));

		System.out.println("---"
				+ getRorgFuncTypeAndFriendlynameFromConfigFile(""));

		System.out.println("---"
				+ getRorgFuncTypeAndFriendlynameFromConfigFile("0x66655544"));

		// 0x12345678 == int 305419896
		System.out.println("---"
				+ Utils.bytesToHexString(Utils.intTo4Bytes(305419896)));

		System.out.println("---"
				+ ConfigurationFileManager
						.getRorgFuncTypeAndFriendlynameFromConfigFile("0x"
								+ Utils.bytesToHexString(Utils
										.intTo4Bytes(305419896))));

		// 0x00123456 == int 1193046
		System.out.println("---"
				+ Utils.bytesToHexString(Utils.intTo4Bytes(1193046)));

		System.out.println("---"
				+ ConfigurationFileManager
						.getRorgFuncTypeAndFriendlynameFromConfigFile("0x"
								+ Utils.bytesToHexString(Utils
										.intTo4Bytes(1193046))));

	}

	/**
	 * Get the RORG FUNC TYPE, and FriendlyName from the configuration file
	 * located at: "." + File.separator + "enocean_config" + File.separator +
	 * "enocean_config.txt". This method (re)loads the configuration file each
	 * time. The configuration file is expected to contain lines that are in
	 * line with the following pattern:
	 * 0x12345678_RORG_FUNC_TYPE_FRIENDLYNAME=A1-02-01-Bla bla-bla friendly
	 * bla-bla name ;-) where RORG, FUNC, TYPE, and Friendlyname are separated
	 * by a "##". In the example, the Rorg is: "A1", the Func is: "02", the Type
	 * is: "01", and the Friendlyname is:
	 * "Bla bla-bla friendly bla-bla name ;-)". The friendly name may contain
	 * spaces, but can not contain "##".
	 * 
	 * @param enOceanId
	 *            as an hexa value written as follow, e.g. 0x12345678.
	 * @return the associated RORG-FUNC-TYPE-FRIENDLYNAME object if present in
	 *         the configuration file, e.g. A0 02 01 Water Sensor_45-17-62.
	 *         Return null if nothing is associated to the given enOceanId, or
	 *         if no configuration file is available, or if there is another
	 *         IOException when reading the file, for example.
	 */
	public static RorgFuncTypeFriendlyname getRorgFuncTypeAndFriendlynameFromConfigFile(
			String enOceanId) {
		RorgFuncTypeFriendlyname result = null;

		// Location of the configuration file.
		String configFilePath = "." + File.separator + "enocean_config"
				+ File.separator + "enocean_config.txt";
		File configFile = new File(configFilePath);
		System.out.println("configFile: " + configFile);
		if (configFile.exists()) {
			System.out.println("DEBUG: The conf file exists: configFile: "
					+ configFile);
			// Load current conf file.
			try {
				FileInputStream fis = new FileInputStream(configFile);
				config.load(fis);
				System.out
						.println("DEBUG: Conf file has properly been loaded: config: "
								+ config);

				String enOceanIdKey = enOceanId
						+ "_RORG_FUNC_TYPE_FRIENDLYNAME";
				if (config.containsKey(enOceanIdKey)) {
					System.out.println("DEBUG: The given enOceanId: "
							+ enOceanId
							+ " appears in the conf file, via the expected: "
							+ enOceanIdKey + " key.");
					String valueAssociatedToEnOceanIdKey = config
							.getProperty(enOceanIdKey);
					System.out.println("DEBUG: Its associated value is: "
							+ valueAssociatedToEnOceanIdKey);

					// From String to RorgFuncTypeFriendlyname.
					String rorg = null;
					String func = null;
					String type = null;
					String friendlyname = null;
					// Get rorg
					int firstDashIndex = valueAssociatedToEnOceanIdKey
							.indexOf("##");
					rorg = valueAssociatedToEnOceanIdKey.substring(0,
							firstDashIndex);
					if ("".equals(rorg)) {
						rorg = null;
					}
					valueAssociatedToEnOceanIdKey = valueAssociatedToEnOceanIdKey
							.substring(firstDashIndex + 2);
					// Get func
					int secondDashIndex = valueAssociatedToEnOceanIdKey
							.indexOf("##");
					func = valueAssociatedToEnOceanIdKey.substring(0,
							secondDashIndex);
					if ("".equals(func)) {
						func = null;
					}
					valueAssociatedToEnOceanIdKey = valueAssociatedToEnOceanIdKey
							.substring(secondDashIndex + 2);
					// Get type
					int thirdDashIndex = valueAssociatedToEnOceanIdKey
							.indexOf("##");
					type = valueAssociatedToEnOceanIdKey.substring(0,
							thirdDashIndex);
					if ("".equals(type)) {
						type = null;
					}
					valueAssociatedToEnOceanIdKey = valueAssociatedToEnOceanIdKey
							.substring(thirdDashIndex + 2);
					// Get friendlyname
					friendlyname = valueAssociatedToEnOceanIdKey;
					if ("".equals(friendlyname)) {
						friendlyname = null;
					}

					result = new RorgFuncTypeFriendlyname(rorg, func, type,
							friendlyname);

					return result;
				} else {
					System.out.println("DEBUG: The given enOceanId: "
							+ enOceanId + " does NOT appear in the conf file.");
					return result;
				}
			} catch (IOException e) {
				System.out
						.println("DEBUG: Conf file has NOT properly been loaded: config: "
								+ config);
				e.printStackTrace();
				return result;
			}
		} else {
			try {
				throw new IOException(
						"The configuration file is expected to be available at: "
								+ configFilePath + ", but it is not there.");
			} catch (IOException e) {
				e.printStackTrace();
				return result;
			}
		}
	}

}
