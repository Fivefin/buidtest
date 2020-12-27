package com.example.buildtest.task6

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.fragment_musicplayer.*
import kotlinx.android.synthetic.main.fragment_musicplayer.seekBar2
import kotlinx.android.synthetic.main.fragment_musicplayer.textView_count
import kotlinx.android.synthetic.main.fragment_musicplayer.textView_mn
import java.io.IOException
import kotlin.concurrent.thread



class music : Fragment() {

    val mediaPlayer = MediaPlayer()
    val MList = mutableListOf<String>()
    val MNList = mutableListOf<String>()
    val TAG = "MainActivity3"
    var isPause = false
    var current = 0

    companion object {
        fun newInstance() = music()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_musicplayer, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        mediaPlayer.setOnPreparedListener{
            it.start()
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
        }catch (e: IOException){
            e.printStackTrace()
        }
    }


}