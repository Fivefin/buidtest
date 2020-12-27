package com.example.buildtest.task7

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_main4.*
import java.io.IOException
import kotlin.concurrent.thread


class MainActivity4 : AppCompatActivity() {

    val mediaPlayer = MediaPlayer()
    val MList = mutableListOf<String>()
    val MNList = mutableListOf<String>()
    val TAG = "MainActivity4"
    var isPause = false
    var current = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        mediaPlayer.setOnPreparedListener{
            it.start()
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),0)
        }else{
            getMusicList()
        }
        thread {
            while (true){
                Thread.sleep(200)
                runOnUiThread{
                    seekBar2.max=mediaPlayer.duration
                    seekBar2.progress=mediaPlayer.currentPosition
                }
            }
        }
        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar,
                                           progress: Int,
                                           fromUser: Boolean)
            {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
        })
        mediaPlayer.setOnCompletionListener {
            current++
            if(current >= MList.size){
                current=0
            }
            play()
        }
    }
    fun onPlay(v:View){
        play()
    }
    fun onPause(v:View){
        if(isPause){
            mediaPlayer.start()
            isPause=false
        }else{
            mediaPlayer.pause()
            isPause=true
        }
    }
    fun onStop(v:View){
        mediaPlayer.stop()
    }
    fun onPrev(v:View){
        current--
        if(current < 0){
            current=MList.size-1
        }
        play()
    }
    fun onNext(v:View){
        current++
        if(current >= MList.size){
            current=0
        }
        play()
    }

    fun play(){
        if(MList.size==0) return
        val path=MList[current]
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
            textView_mn.text=MNList[current]
            textView_count.text = "${current+1}/${MList.size}"
        }catch (e:IOException){
            e.printStackTrace()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getMusicList()
    }

    private fun getMusicList(){
        val csr = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null)
        if(csr != null){
            while(csr.moveToNext()){
                val MPath =csr.getString(csr.getColumnIndex(MediaStore.Audio.Media.DATA))
                MList.add(MPath)
                val MNPath =csr.getString(csr.getColumnIndex(MediaStore.Audio.Media.TITLE))
                MNList.add(MNPath)
                Log.d(TAG,"getMusicList : $MPath  name:$MNPath")
            }
            csr.close()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}