package com.example.bluetoothcontroller.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class BTClient extends Thread {

	private String           mDevName;
	private BluetoothAdapter mAdapter;
	private BluetoothSocket  mSocket;
	private BluetoothDevice  mDevice;
	private InputStream      mIn;
	private OutputStream     mOut;
	
	static final UUID BluetoothSerialUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	public BTClient(String name) {
		mDevName = name;
	}
	
	public BluetoothSocket connect() {
		
        try {
        	
        	mAdapter = BluetoothAdapter.getDefaultAdapter();
        	
        	if (mAdapter == null) {
        		throw new Exception("Failed to load BT");
        	}
        	
        	if (!mAdapter.isEnabled()) {
        		throw new Exception("BT not enabled");
        	}
        	
        	for (BluetoothDevice bd : mAdapter.getBondedDevices()) {
        		if (bd.getName().compareTo(mDevName) == 0) {
        			mDevice = bd;
        			break;
        		}
        		
        		Log.e("BT", "found: " + bd.getName());
        	}
        	
        	if (mDevice == null) {
        		throw new Exception("device not found");
        	}
        	
        	mSocket = mDevice.createRfcommSocketToServiceRecord(BluetoothSerialUuid);
        	        	
        	mSocket.connect();
        	
        	mIn  = mSocket.getInputStream();
        	mOut = mSocket.getOutputStream();
        	
        	Log.e("BT", "in = " + mIn);
        	Log.e("BT", "out = " + mOut);
        	
        } catch (IOException e) {
        	
        	try {
				mSocket.close();
			} catch (IOException e1) { }
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return mSocket;
	}
	
	public void run() {
		byte[] buffer = new byte[1024];
		
		int bytes;
		
		while (true) {
			try {
				
				mOut.write(new byte[]{'A'});
				bytes = mIn.read(buffer);
				sleep(1000);
				
				Log.e("BT", "byte = " + ((int) (buffer[0])));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void cancel() {
		try {
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
