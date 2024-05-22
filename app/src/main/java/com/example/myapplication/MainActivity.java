package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    // Manages bluetooth functionality

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            try{
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceAddress = device.getAddress();
                    Toast.makeText(context, "Discovered: " +
                                    deviceName + " [" + deviceAddress + "]",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Bluetooth permission denied",
                            Toast.LENGTH_SHORT).show();
                }
            }catch (SecurityException e) {
                Toast.makeText(context, "Security Exception " +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    };//Handles bluetooth discovery events

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the view to activity.main.xml
        setContentView(R.layout.activity_main);

        //Get default BluetoothAdapter for the device
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Check if the device supports bluetooth
        if (bluetoothAdapter == null) {
            //If bluetooth is not supported display a message and then
            //close the app
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //Check if bluetooth is enabled
        if (!bluetoothAdapter.isEnabled()){
            //If it is not enabled request the user to enable it
            Intent enableBtIntent =
                    new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        } else {
            //If bluetooth is enabled start the discovery process
            startDiscovery();
        }
    }

    private void startDiscovery(){

    }

}