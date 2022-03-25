package com.shong.stringconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stringConverter = StringConverter()
        val textView = findViewById<TextView>(R.id.textView)
        textView.setText(stringConverter.colorChange(textView.text.toString(), "sHong", "#00FF00"))
    }
}