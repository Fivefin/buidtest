package com.example.buildtest.task4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_stop_watch.*

class stopWatchActivity : AppCompatActivity() {
    var seconds = 0
    var running = false
    var wasrunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)
        if(savedInstanceState != null) {
//          在旋转屏幕的时候恢复数据
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasrunning = savedInstanceState.getBoolean("wasrunning")
        }
        runTimer()
        button_start.setOnClickListener {
            running = true
        }

        button_stop.setOnClickListener {
            running = false
        }

        button_restart.setOnClickListener {
            running = true
            seconds = 0
        }
        button_close.setOnClickListener {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds",seconds)
        outState.putBoolean("running",running)
        outState.putBoolean("wasrunning",wasrunning)
    }

    private fun runTimer() {
//        系统调用runnable
        val handler = Handler()
//        接口
        val runnable = object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60
                textView_timer.text = String.format("%02d:%02d:%02d",hours,minutes,secs)
                if(running) {
                    seconds++
                }
                handler.postDelayed(this,1000)
            }
        }
        handler.post(runnable)
    }
}