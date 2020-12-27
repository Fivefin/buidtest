package com.example.buildtest.task10

data class City(
    val area_code: Any,
    val city_code: String,
    val city_name: String,
    val ctime: Any,
    val id: Int,
    val pid: Int,
    val post_code: Any
){
    override fun toString(): String {
        return city_name
    }
}
