package com.example.bluetooth

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bluetooth.JDY08Manager

class MainActivity : AppCompatActivity() {
    private lateinit var manager: JDY08Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = JDY08Manager(this)
        findViewById<Button>(R.id.button_on).setOnClickListener {
            manager.sendCommand("1")
        }
        findViewById<Button>(R.id.button_off).setOnClickListener {
            manager.sendCommand("0")
        }
        manager.connect()
    }

}
