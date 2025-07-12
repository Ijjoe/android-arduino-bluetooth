package com.example.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import java.util.UUID

class JDY08Manager(private val context: Context, private val deviceName: String = "JDY-08") {
    private val adapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var gatt: BluetoothGatt? = null
    private val serviceUUID: UUID = UUID.fromString("0000FFE0-0000-1000-8000-00805F9B34FB")
    private val charUUID: UUID = UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB")

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                this@JDY08Manager.gatt = gatt
                gatt.discoverServices()
            }
        }
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if (result.device.name == deviceName) {
                adapter.bluetoothLeScanner.stopScan(this)
                result.device.connectGatt(context, false, gattCallback)
            }
        }
    }

    fun connect() {
        val scanner = adapter.bluetoothLeScanner
        val filter = ScanFilter.Builder().setDeviceName(deviceName).build()
        val settings = ScanSettings.Builder().build()
        scanner.startScan(listOf(filter), settings, scanCallback)
    }

    fun sendCommand(cmd: String) {
        val characteristic: BluetoothGattCharacteristic =
            gatt?.getService(serviceUUID)?.getCharacteristic(charUUID) ?: return
        characteristic.setValue(cmd)
        gatt?.writeCharacteristic(characteristic)
    }
}
