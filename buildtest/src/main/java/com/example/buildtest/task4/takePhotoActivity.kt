package com.example.buildtest.task4

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_take_photo.*

class takePhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_photo)

        button.setOnClickListener {
            takeP()
        }

        button1.setOnClickListener {
            finish()
        }
    }
    val NUM = 1
    private fun takeP() {
        val takePhotointent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePhotointent,NUM)
        }
        else {
            Log.d("a","erro")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == NUM && resultCode == Activity.RESULT_OK) {
            val imageBitMap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitMap)
        }
    }
}