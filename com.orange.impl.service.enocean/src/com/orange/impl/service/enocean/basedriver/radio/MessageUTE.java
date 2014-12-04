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
import com.orange.impl.service.enocean.utils.Utils;

/**
 * MESSAGE_UTE, see EnOcean_Equipment_Profiles_EEP_V2.61_public.pdf, page 167,
 * 3.6) UTE - Universla Uni- and Bidirectional Teach-in.
 */
public class MessageUTE extends Message {

	/**
	 * TAG
	 */
	public static final String TAG = "MessageUTE";

	/**
	 * See section 3.6) UTE - Universal Uni- and Bidirectional Teach-in, page
	 * 167 of EnOcean_Equipment_Profiles_EEP_V2.61_public.pdf
	 * 
	 * @param data
	 *            , i.e the data, and the optional data parts of an EnOcean
	 *            packet/telegram (see, section "1.6.1 Packet description", page
	 *            13 of EnOceanSerialProtocol3.pdf). E.g. for a (full) 1BS
	 *            telegram 55000707017ad500008a92390001ffffffff3000eb, then data
	 *            is d500008a92390001ffffffff3000
	 */
	public MessageUTE(byte[] data) {
		super(data);

		Logger.d(TAG, "getPayloadBytes(): " + getPayloadBytes());
		int i = 0;
		while (i < getPayloadBytes().length) {
			Logger.d(
					TAG,
					"getPayloadBytes()[" + i + "]: "
							+ Utils.byteToHexString(getPayloadBytes()[i]));
			i = i + 1;
		}

		// RORG UTE is D4, but here, the RORG of the profile is
		// getPayloadBytes()[0]: d2
		setRORG(getPayloadBytes()[6]);
		setFunc(getPayloadBytes()[5]);
		setType(getPayloadBytes()[4]);
	}

	public boolean isTeachin() {
		// TODO Auto-generated method stub
		return (((getPayloadBytes()[0] & 0x08)) == 0);
	}

	/**
	 * @return true if the message teach-in embeds profile & manufacturer info,
	 *         false otherwise.
	 */
	public boolean hasTeachInInfo() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the FUNC in the case of a teach-in message with information.
	 *         Return -1 if non-relevant.
	 */
	public int teachInFunc() {
		// TODO Auto-generated method stub
		// This is non-relevant with 1BS message.
		return -1;
	}

	/**
	 * @return the TYPE in the case of a teach-in message with information.
	 *         Return -1 if non-relevant.
	 */
	public int teachInType() {
		// TODO Auto-generated method stub
		// This is non-relevant with 1BS message.
		return -1;
	}

	/**
	 * @return the MANUF in the case of a teach-in message with information.
	 *         Return -1 if non-relevant.
	 */
	public int teachInManuf() {
		// TODO Auto-generated method stub
		// This is non-relevant with 1BS message.
		return -1;
	}
}
