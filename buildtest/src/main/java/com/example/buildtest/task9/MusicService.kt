package com.example.buildtest.task9

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_musicplayer.*
import java.io.IOException

class MusicService : Service() {

    val TAG = "MusicService"
    val mediaPlayer = MediaPlayer()
    val MList = mutableListOf<String>()
    val MNList = mutableListOf<String>()
    var isPause = false
    var current = 0
    companion object{
        val Command = "Operate"
    }
    val binder = MusicBinder()

    inner class MusicBinder: Binder(){
        val musicName get()=MNList.get(current)
        var currentPosition get() = mediaPlayer.currentPosition
            set(value) = mediaPlayer.seekTo(value)

        val duration get() = mediaPlayer.duration
        val size get() = MList.size
        val currentIndex get() = current

    }

    override fun onCreate() {
        super.onCreate()
        getMusicList()
        mediaPlayer.setOnPreparedListener {
            it.start()
        }

        mediaPlayer.setOnCompletionListener {
            current++
            if (current >= MList.size) {
                current = 0
            }
            play()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val operate = intent?.getIntExtra(Command,1) ?: 1
        when(operate){
            1 -> play()
            2 ->Pause()
            3 ->Stop()
            4 ->Next()
            5 ->Prev()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    fun Pause() {
        if (isPause) {
            mediaPlayer.start()
            isPause = false
        } else {
            mediaPlayer.pause()
            isPause = true
        }
    }

    fun Stop() {
        mediaPlayer.stop()
        stopSelf()
    }

    fun Prev() {
        current--
        if (current < 0) {
            current = MList.size - 1
        }
        play()
    }

    fun Next() {
        current++
        if (current >= MList.size) {
            current = 0
        }
        play()
    }
    fun play() {
        if (MList.size == 0) return
        val path = MList[current]
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun getMusicList() {
        val csr = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (csr != null) {
            while (csr.moveToNext()) {
                val MPath = csr.getString(csr.getColumnIndex(MediaStore.Audio.Media.DATA))
                MList.add(MPath)
                val MNPath = csr.getString(csr.getColumnIndex(MediaStore.Audio.Media.TITLE))
                MNList.add(MNPath)
                Log.d(TAG, "getMusicList : $MPath  name:$MNPath")
            }
            csr.close()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

}
