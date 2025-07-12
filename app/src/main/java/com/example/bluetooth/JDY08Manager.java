package com.example.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class JDY08Manager {
    private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothGatt gatt;
    private final Context context;
    private final String deviceName;
    private BluetoothLeScanner scanner;
    private ScanCallback scanCallback;

    private final UUID serviceUUID = UUID.fromString("0000FFE0-0000-1000-8000-00805F9B34FB");
    private final UUID charUUID = UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB");

    public interface DeviceCallback {
        void onDeviceFound(BluetoothDevice device);
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                JDY08Manager.this.gatt = gatt;
                gatt.discoverServices();
            }
        }
    };

    public JDY08Manager(Context context) {
        this(context, "JDY-08");
    }

    public JDY08Manager(Context context, String deviceName) {
        this.context = context;
        this.deviceName = deviceName;
    }

    public void startScan(DeviceCallback callback) {
        scanner = adapter.getBluetoothLeScanner();
        if (scanner == null) return;
        ScanFilter filter = new ScanFilter.Builder().setDeviceName(deviceName).build();
        ScanSettings settings = new ScanSettings.Builder().build();
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                BluetoothDevice device = result.getDevice();
                if (deviceName.equals(device.getName())) {
                    callback.onDeviceFound(device);
                }
            }
        };
        scanner.startScan(Arrays.asList(filter), settings, scanCallback);
    }

    public void stopScan() {
        if (scanner != null && scanCallback != null) {
            scanner.stopScan(scanCallback);
        }
    }

    public void connect(BluetoothDevice device) {
        if (device != null) {
            device.connectGatt(context, false, gattCallback);
        }
    }

    public void sendCommand(String cmd) {
        if (gatt == null) return;
        BluetoothGattService service = gatt.getService(serviceUUID);
        if (service == null) return;
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(charUUID);
        if (characteristic == null) return;
        characteristic.setValue(cmd);
        gatt.writeCharacteristic(characteristic);
    }
}
