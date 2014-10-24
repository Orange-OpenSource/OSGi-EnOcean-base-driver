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

package com.orange.impl.service.enocean.basedriver.radio;

import com.orange.impl.service.enocean.utils.Logger;

/**
 * Message1BS: Prototype of a 1BS (EnOcean 1 Byte Communication) telegram.
 * 
 * EnOcean_Equipment_Profiles_EEP_V2.61_public.pdf, page 10.
 * 
 * RORG - 1 byte - 0xD5
 * 
 * Data DB_0 - 1 byte
 * 
 * Sender ID - 4 bytes
 * 
 * Status - 1 byte
 * 
 * EnOcean_Equipment_Profiles_EEP_V2.61_public.pdf, page 24.
 * 
 * D5: 1BS Telegram
 * 
 * D5-00: Contacts and Switches
 * 
 * RORG D5 1BS Telegram
 * 
 * FUNC 00 Contacts and Switches
 * 
 * TYPE 01 Single Input Contact
 * 
 * Offset | Size | Bitrange | Data | ShortCut | Description | Valid Range
 * 
 * 4 | 1 | DB0.3 | Learn Button | LRN | ... | Enum: 0: pressed 1: not pressed
 * 
 * 7 | 1 | DB0.0 | Contact | CO | ... | Enum: 0: open 1: not closed
 */
public class Message1BS extends Message {

	/**
	 * TAG
	 */
	public static final String TAG = "Message1BS";

	/**
	 * @param data
	 *            , i.e the data, and the optional data parts of an EnOcean
	 *            packet/telegram (see, section "1.6.1 Packet description", page
	 *            13 of EnOceanSerialProtocol3.pdf). E.g. for a (full) 1BS
	 *            telegram 55000707017ad500008a92390001ffffffff3000eb, then data
	 *            is d500008a92390001ffffffff3000
	 */
	public Message1BS(byte[] data) {
		super(data);
	}

	public boolean isTeachin() {
		return (((getPayloadBytes()[0] & 0x08)) == 0);
	}

	/**
	 * @return true if the message teach-in embeds profile & manufacturer info.
	 */
	public boolean hasTeachInInfo() {
		// TODO AAA: 1BS to be implemented
		Logger.d(TAG, "// TODO AAA: 1BS to be implemented");
		return (getPayloadBytes()[3] & 0x80) != 0;
	}

	/**
	 * @return the FUNC in the case of a teach-in message with information.
	 */
	public int teachInFunc() {
		// TODO AAA: 1BS to be implemented
		Logger.d(TAG, "// TODO AAA: 1BS to be implemented");
		return (getPayloadBytes()[0] >> 2) & 0xff;
	}

	/**
	 * @return the TYPE in the case of a teach-in message with information.
	 */
	public int teachInType() {
		// TODO AAA: 1BS to be implemented
		Logger.d(TAG, "// TODO AAA: 1BS to be implemented");
		byte b0 = getPayloadBytes()[0];
		byte b1 = getPayloadBytes()[1];
		return (((b0 & 0x03) << 5) & 0xff) | ((((b1 >> 3)) & 0xff));
	}

	/**
	 * @return the MANUF in the case of a teach-in message with information.
	 */
	public int teachInManuf() {
		// TODO AAA: 1BS to be implemented
		Logger.d(TAG, "// TODO AAA: 1BS to be implemented");
		byte b0 = (byte) ((getPayloadBytes()[1]) & 0x07);
		byte b1 = getPayloadBytes()[2];
		return ((b0 & 0xff) << 8) + (b1 & 0xff);
	}
}
