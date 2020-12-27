package com.example.buildtest.task10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.buildtest.R
import com.example.buildtest.task10.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main7.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity7 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

//        button_load.setOnClickListener{
//            try{
//                thread{
//                    val imgUrl = "https://cn.bing.com/images/search?view=detailV2&ccid=KeD9guSo&id=FC96822918FAF5B1D994FCD885D5ADCE49D9D498&thid=OIP.KeD9guSotaINym8Ip8F3cQHaHa&mediaurl=http%3a%2f%2fimg1.juimg.com%2f160418%2f330694-16041R2335111.jpg&exph=1024&expw=1024&q=%e5%b0%8f%e9%b8%9f%e8%88%8d&simid=608003301835802949&ck=8BFC1C236BD5E49A533002B6D51C5C7F&selectedIndex=37&FORM=IRPRST&ajaxhist=0"
//                    val url = URL(imgUrl)
//                    val conn = url.openConnection() as HttpURLConnection
//                    conn.connectTimeout = 8000
//                    conn.readTimeout = 8000
//
//
//                }
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }

        thread {
            var str = readFileFromRaw(R.raw.citycode)
            val gson = Gson()
            val CityType = object :TypeToken<List<City>>(){}.type
            var citys:List<City> = gson.fromJson(str,CityType)
            var cities:List<City> = citys.filter { it.city_code!="" }
            runOnUiThread{
                val adapter = ArrayAdapter<City>(this,android.R.layout.simple_list_item_1,cities)
                listView_weather.adapter = adapter
                listView_weather.setOnItemClickListener { _, _, position, _ ->
                    val cityCode = cities[position].city_code
                    val intent = Intent(this, Main2Activity7::class.java)
                    intent.putExtra("city_code",cityCode)
                    Log.d("MainActivity",cityCode)
                    startActivity(intent)
                }

            }

            Log.d("MainActivity","$cities")
        }

//        button_load2.setOnClickListener{
//            val jsonURL="http://t.weather.itboy.net/api/weather/city/101030100"
//            val imgURL="https://cn.bing.com/images/search?view=detailV2&ccid=KeD9guSo&id=FC96822918FAF5B1D994FCD885D5ADCE49D9D498&thid=OIP.KeD9guSotaINym8Ip8F3cQHaHa&mediaurl=http%3a%2f%2fimg1.juimg.com%2f160418%2f330694-16041R2335111.jpg&exph=1024&expw=1024&q=%e5%b0%8f%e9%b8%9f%e8%88%8d&simid=608003301835802949&ck=8BFC1C236BD5E49A533002B6D51C5C7F&selectedIndex=37&FORM=IRPRST&ajaxhist=0"
//
//            val queue = Volley.newRequestQueue(this)
//            val stringRequest = StringRequest(jsonURL,{
//                val gson=Gson()
//                val typeOf = object: TypeToken<List<Weather>>(){}.type
//                val weather = gson.fromJson<Weather>(it,typeOf)
//                Log.d("result","$(weather.cityInfo.city)")
//                },{
//                Log.d("error","$it")
//            })
//        }

    }

    fun readFileFromRaw(rawName: Int): String? {
        try {
            val inputReader = InputStreamReader(resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var result: String? = ""
            while (bufReader.readLine().also({ line = it }) != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}