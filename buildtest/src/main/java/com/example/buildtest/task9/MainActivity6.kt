package com.example.buildtest.task9

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.buildtest.R
import kotlinx.android.synthetic.main.fragment_musicplayer.*
import java.io.IOException
import kotlin.concurrent.thread


class MainActivity6 : AppCompatActivity(),ServiceConnection {
    var binder: MusicService.MusicBinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
        } else {
            startMService()
        }

        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    binder?.currentPosition = progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
        })

    }
    fun startMService(){
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Command,1)
        startService(intent)
        bindService(intent,this,Context.BIND_AUTO_CREATE)
    }

    fun onPlay(v:View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Command,1)
        startService(intent)
    }
    fun onPause(v:View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Command,2)
        startService(intent)
    }
    fun onStop(v:View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Command,3)
        startService(intent)
    }
    fun onNext(v:View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Command,4)
        startService(intent)
    }
    fun onPrev(v:View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Command,5)
        startService(intent)
    }





    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        binder = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        binder = service as MusicService.MusicBinder
        thread {
            while (null != binder) {
                Thread.sleep(200)
                runOnUiThread {
                    seekBar2.max = binder!!.duration
                    seekBar2.progress = binder!!.currentPosition
                    textView_mn.text = binder!!.musicName
                    textView_count.text = "${binder!!.currentIndex}/${binder!!.size}"
                }
            }
        }
    }
}
