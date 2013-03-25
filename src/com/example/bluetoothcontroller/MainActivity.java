package com.example.bluetoothcontroller;

import java.io.IOException;
import java.util.UUID;

import com.example.bluetoothcontroller.bluetooth.BTClient;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private BTClient mBTClient;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mBTClient = new BTClient("linvor");
        mBTClient.connect();
        mBTClient.start();
        
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
