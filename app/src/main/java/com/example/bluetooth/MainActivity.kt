package com.example.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val deviceAddress = "00:00:00:00:00:00" // TODO: set your module MAC address
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var bluetoothSocket: BluetoothSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_on).setOnClickListener {
            sendCommand("1")
        }
        findViewById<Button>(R.id.button_off).setOnClickListener {
            sendCommand("0")
        }
    }

    private fun sendCommand(cmd: String) {
        if (bluetoothSocket == null || !bluetoothSocket!!.isConnected) {
            connectSocket()
        }
        try {
            bluetoothSocket?.outputStream?.write(cmd.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun connectSocket() {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val device: BluetoothDevice = adapter.getRemoteDevice(deviceAddress)
        bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
        adapter.cancelDiscovery()
        try {
            bluetoothSocket?.connect()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}