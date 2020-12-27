package com.example.buildtest.task4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_main.*

const val MESSAGE = "MESSAGE"
const val RE_MESSAGE = "RE_MESSAGE"
const val REQUESTCODE = 1
class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            val intent = Intent(this,sendActivity::class.java)
            intent.putExtra(MESSAGE,editText1.text.toString())
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this,reciveActivity::class.java)
            startActivityForResult(intent, REQUESTCODE)
        }

        button3.setOnClickListener {
            val intent = Intent(this,takePhotoActivity::class.java)
            startActivity(intent)
        }
        button4.setOnClickListener {
            val intent = Intent(this,stopWatchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUESTCODE) {
            if (resultCode == REQUESTCODE) {
                textView1.text = data?.getStringExtra(RE_MESSAGE)
            }
        }
    }
}