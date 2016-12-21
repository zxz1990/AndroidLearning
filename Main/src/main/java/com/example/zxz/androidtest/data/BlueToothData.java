package com.example.zxz.androidtest.data;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.List;
import java.util.Set;

/**
 * Created by 学智 on 2016-01-03.
 */
public class BlueToothData {

    private static final String TAG = "BlueToothData";

    private static BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();

    public boolean isOpen() {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        return btAdapter.isEnabled();
    }

    @Deprecated
    public static List<BluetoothDevice> getConnectedDevices(Context context) {
        BluetoothManager btManager = (BluetoothManager)context.getSystemService(Context.BLUETOOTH_SERVICE);
        List<BluetoothDevice> devices = btManager.getConnectedDevices(BluetoothProfile.GATT);
        Log.d(TAG, String.valueOf(devices));
        return devices;
    }

    public static Set<BluetoothDevice> getBondedDevices(Context context) {
        if(mBTAdapter.isEnabled() == false) {
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        Set<BluetoothDevice> devices = mBTAdapter.getBondedDevices();
        Log.d(TAG, String.valueOf(devices));
        return devices;
    }
}
