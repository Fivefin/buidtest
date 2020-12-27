package com.example.buildtest.task4


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_send.*

class sendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        val intent = getIntent()
        textView.text = intent.getStringExtra("MESSAGE")
        button.setOnClickListener {
            finish()
        }
    }
}