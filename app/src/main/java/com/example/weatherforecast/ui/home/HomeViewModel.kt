package com.example.weatherforecast.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.data.api.Response

class HomeViewModel : ViewModel() {
    lateinit var locationMutableLiveData : MutableLiveData<Response>

}