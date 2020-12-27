package com.example.buildtest.task10.weather

data class Weather(
    val cityInfo: CityInfo,
    val `data`: Data,
    val date: String,
    val message: String,
    val status: Int,
    val time: String
)