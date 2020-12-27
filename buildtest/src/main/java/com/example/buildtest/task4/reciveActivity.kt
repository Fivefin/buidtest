package com.example.buildtest.task4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_recive.*

class reciveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recive)
        val resultCode = 1
        button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("RE_MESSAGE",editText.text.toString())
            setResult(resultCode,intent)
            finish()
        }
    }
}