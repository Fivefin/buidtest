package com.example.buildtest.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buildtest.R
import kotlinx.android.synthetic.main.activity_main3.*


class MainActivity3 : AppCompatActivity() {

    val gameFragment = cardGame.newInstance()
    val chatFragment = chat.newInstance()
    val musicFragment = music.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        //新建fragment
        if (savedInstanceState == null) {
            //开启事务
            supportFragmentManager.beginTransaction()
                .replace(R.id.framelayout,gameFragment)
                .commit()
        }
        //点击底部导航栏事件
        bottomNav.setOnNavigationItemReselectedListener{
            when(it.itemId) {
                R.id.navigation_card ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,gameFragment)
                        .commit()
                R.id.navigation_chat ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,chatFragment)
                        .commit()
                R.id.navigation_music ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,musicFragment)
                        .commit()
            }
            true
        }
    }
}
