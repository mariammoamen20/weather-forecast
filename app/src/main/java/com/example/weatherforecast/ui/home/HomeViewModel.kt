package com.example.weatherforecast.ui.home

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.Constant
import com.example.weatherforecast.data.api.*
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Callback

class HomeViewModel() : ViewModel() {
    var currentMutableLiveData=MutableLiveData<Current?>()
    var messageMutableLiveData = MutableLiveData<String>()
    var hourlyMutableLiveData = MutableLiveData<List<HourlyItem?>?>()
    var dailyMutableLiveData = MutableLiveData<List<DailyItem?>?>()
    var data = ObservableField<String>()

    fun fetchWeatherByLocation(latitude: Int, longitude: Int, units: String) {
        ApiManger.getApis().getWeather(latitude, longitude, Constant.API_KEY, units = units)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    //hold data from api into live data
                    currentMutableLiveData.value=response.body()?.current
                    hourlyMutableLiveData.value = response.body()?.hourly
                    dailyMutableLiveData.value  =response.body()?.daily

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    messageMutableLiveData.value = t.localizedMessage
                }

            })

    }

}