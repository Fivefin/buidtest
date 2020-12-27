package com.example.buildtest.task10

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.buildtest.R
import com.example.buildtest.task10.weather.Forecast
import com.example.buildtest.task10.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity2_main7.*

class Main2Activity7 : AppCompatActivity() {

    val baseURL = "http://t.weather.itboy.net/api/weather/city/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)
        val cityCode = intent.getStringExtra("city_code")
        Log.d("Main2Activity7","cityCode:"+cityCode)
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(baseURL+cityCode,{
            val gson = Gson()
            val WeatherType = object : TypeToken<Weather>(){}.type
            val weather = gson.fromJson<Weather>(it,WeatherType)
            textView_city.text = weather.cityInfo.city
            textView_province.text = weather.cityInfo.parent
            textView_temperature.text = weather.data.shidu
            textView_humidity.text = weather.data.wendu
            val firstDay = weather.data.forecast.first()
            when(firstDay.type){
                "小雨" -> image.setImageResource(R.drawable.rain)
                "阴" -> image.setImageResource(R.drawable.cloud)
                "多云" -> image.setImageResource(R.drawable.mcloud)
                "晴" -> image.setImageResource(R.drawable.sun)
                "雪" -> image.setImageResource(R.drawable.snow)
                else -> image.setImageResource(R.drawable.thunder)
            }
            val adapter = ArrayAdapter<Forecast>(this,android.R.layout.simple_list_item_1,weather.data.forecast)
            listView.adapter = adapter

            Log.d("MainActivity2","cc$it")
        },{
            Log.d("MainActivity2","cc$it")
        })
        queue.add(stringRequest)
    }

}