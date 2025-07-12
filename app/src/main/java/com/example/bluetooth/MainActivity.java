package com.example.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private JDY08Manager manager;
    private ArrayAdapter<String> listAdapter;
    private final List<BluetoothDevice> devices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new JDY08Manager(this);

        ListView listView = findViewById(R.id.list_devices);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);

        manager.startScan(device -> runOnUiThread(() -> {
            if (!devices.contains(device)) {
                devices.add(device);
                listAdapter.add(device.getName() + " - " + device.getAddress());
            }
        }));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            manager.stopScan();
            BluetoothDevice device = devices.get(position);
            manager.connect(device);
        });

        Button onButton = findViewById(R.id.button_on);
        Button offButton = findViewById(R.id.button_off);

        onButton.setOnClickListener(v -> manager.sendCommand("1"));
        offButton.setOnClickListener(v -> manager.sendCommand("0"));
    }
}
