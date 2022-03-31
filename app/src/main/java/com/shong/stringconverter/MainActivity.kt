package com.shong.stringconverter

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stringConverter = StringConverter(this)
        val textView = findViewById<TextView>(R.id.textView)
        textView.setText(stringConverter.colorChange(textView.text.toString(), "sHong", "#00FF00"))
        val textView2 = findViewById<TextView>(R.id.textView2)
        textView2.setText(stringConverter.fontChange(textView2.text.toString(), "sHong"))
    }
}