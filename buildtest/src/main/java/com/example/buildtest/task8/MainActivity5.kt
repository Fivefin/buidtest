package com.example.buildtest.task8

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_main5.*

class MainActivity5 : AppCompatActivity(){

    val CID = "MyChannel"
    val NID = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        button_ntf.setOnClickListener{
            val intent = Intent(this,MainActivity5::class.java)
            val pIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder: Notification.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val ntfChannel = NotificationChannel(CID,"test",NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(ntfChannel)
                builder = Notification.Builder(this,CID)
            }else{
                builder = Notification.Builder(this)
            }
            val notification = builder.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("通知测试")
                .setContentText("这是一个通知")
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(NID,notification)


        }
    }
}