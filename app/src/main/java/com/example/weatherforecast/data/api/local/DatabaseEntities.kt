package com.example.weatherforecast.data.api.local

import androidx.room.Entity


@Entity(primaryKeys = ["lat", "lng"])
    data class City(
    val lat: Int?,
    val lng: Int?,
    val location: String,
    val date: Int?,
    val temp: Double?,
    val max_temp: Double?,
    val min_temp: Double?,
    val icon: String?
    )
