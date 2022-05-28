package com.example.weatherforecast

import java.text.SimpleDateFormat
import java.util.*

object Constant {
    const val API_KEY = "18b90dd81ef1843c4c0a6563fc2fae77"

    fun unixTimeStampToDataTime(unixTimeStamp:Int):String{
        val dateFormat=SimpleDateFormat("HH:mm a")
        val date= Date()
        date.time=unixTimeStamp.toLong()*1000
        return dateFormat.format(date)
    }
    val dateNow: String
        get() {
            val dateFormat = SimpleDateFormat("EEEE dd/MM/yyyy ")
            val date = Date()
            return dateFormat.format(date)
        }

    val timeNow:String
    get(){
        val dateFormat = SimpleDateFormat(" HH:mm a ")
        val date = Date()
        return dateFormat.format(date)
    }
}
