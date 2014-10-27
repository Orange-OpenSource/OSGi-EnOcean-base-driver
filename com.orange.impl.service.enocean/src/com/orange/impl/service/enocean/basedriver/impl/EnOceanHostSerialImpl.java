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

import gnu.io.RXTXPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.TooManyListenersException;

import org.osgi.framework.BundleContext;

import com.orange.impl.service.enocean.basedriver.esp.EspPacket;
import com.orange.impl.service.enocean.utils.EnOceanHostImplException;
import com.orange.impl.service.enocean.utils.Logger;
import com.orange.impl.service.enocean.utils.PortInUseException;
import com.orange.impl.service.enocean.utils.Utils;

/**
 * EnOceanHostSerialImpl.
 */
public class EnOceanHostSerialImpl extends EnOceanHostImpl implements
		SerialPortEventListener {

	private static final int ENOCEAN_DEFAULT_TTY_SPEED = 57600;

	private Object syncObject;
	private RXTXPort serialPort;

	/**
	 * @param path
	 * @param bc
	 */
	public EnOceanHostSerialImpl(String path, BundleContext bc) {
		super(path, bc);
		System.out
				.println("EnOceanHostSerialImpl.EnOceanHostSerialImpl(String path: "
						+ path + ", BundleContext bc: " + bc + ")");
		this.syncObject = new Object();
	}

	public void startup() throws EnOceanHostImplException {
		System.out.println("EnOceanHostSerialImpl.startup()");
		this.isRunning = true;
		try {
			openSerialPort(donglePath, ENOCEAN_DEFAULT_TTY_SPEED);
		} catch (Exception exception) {
			throw new EnOceanHostImplException("Could not open serial port: "
					+ exception.getMessage());
		}
		this.start();
	}

	public void run() {
		while (this.isRunning) {
			try {
				synchronized (this.syncObject) {
					if (this.inputStream.available() == 0)
						this.syncObject.wait();
				}
				if (!this.isRunning) {
					return;
				}
				System.out
						.println("EnOceanHostSerialImpl.run(), try to read from this.serialPort.getInputStream()...");
				int _byte = inputStream.read();
				System.out
						.println("EnOceanHostSerialImpl.run(), just read _byte: "
								+ _byte);
				if (_byte == -1) {
					throw new IOException("buffer end was reached");
				}
				Logger.d(
						TAG,
						"read a byte: "
								+ Utils.bytesToHexString(new byte[] { (byte) _byte }));
				if (_byte == ENOCEAN_ESP_FRAME_START) {
					EspPacket packet = readPacket();
					if (packet.getPacketType() == EspPacket.TYPE_RADIO) {
						dispatchToListeners(packet.getFullData());
					}
				}
			} catch (IOException ioexception) {
				Logger.e(TAG, "Error while reading input packet: "
						+ ioexception.getMessage());
				ioexception.printStackTrace();
			} catch (InterruptedException interruptedexception) {
				Logger.e(TAG, "Error while reading input packet: "
						+ interruptedexception.getMessage());
				interruptedexception.printStackTrace();
			} finally {
				try {
					inputStream.skip(inputStream.available());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 */
	public void close() {
		this.isRunning = false;
		synchronized (this.syncObject) {
			this.syncObject.notify();
		}
		if (this.outputStream != null)
			try {
				this.outputStream.close();
			} catch (IOException ioexception) {
				Logger.w(TAG, "Error while closing output stream.");
			}
		if (this.inputStream != null)
			try {
				this.inputStream.close();
			} catch (IOException ioexception1) {
				Logger.w(TAG, "Error while closing input stream.");
			}
		if (this.serialPort != null)
			this.serialPort.close();
	}

	public void send(byte[] data) {
		try {
			outputStream.write(data);
			outputStream.flush();
		} catch (IOException e) {
			Logger.e(TAG, "an exception occured while writing to stream '"
					+ donglePath + "' : " + e.getMessage());
		}

	}

	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == 1) {
			synchronized (this.syncObject) {
				this.syncObject.notify();
			}
		}
	}

	private void openSerialPort(String s, int i) throws IOException,
			PortInUseException, UnsupportedCommOperationException,
			TooManyListenersException, gnu.io.PortInUseException {
		this.serialPort = new RXTXPort(s);
		this.serialPort.setSerialPortParams(i, 8, 1, 0);
		this.serialPort.setFlowControlMode(0);
		this.serialPort.notifyOnDataAvailable(true);
		this.serialPort.addEventListener(this);
		this.inputStream = this.serialPort.getInputStream();
		this.outputStream = this.serialPort.getOutputStream();
	}

}
