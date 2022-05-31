package com.example.weatherforecast.ui.home

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.Constant
import com.example.weatherforecast.R
import com.example.weatherforecast.data.api.Current
import com.example.weatherforecast.data.api.DailyItem
import com.example.weatherforecast.data.api.HourlyItem
import com.example.weatherforecast.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class HomeFragment : Fragment() {
    lateinit var viewBinding: FragmentHomeBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var hourlyRecyclerView: RecyclerView
    lateinit var dailyRecyclerView: RecyclerView
    lateinit var hourlyAdapter: HourlyAdapter
    lateinit var dailyAdapter: DailyAdapter
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return viewBinding.root
    }

    private fun subscribeToLiveData() {
        homeViewModel.currentMutableLiveData.observe(viewLifecycleOwner) {
            showCurrentWeatherData(it)
        }
        homeViewModel.hourlyMutableLiveData.observe(viewLifecycleOwner) {
            showHourlyData(it)
        }
        homeViewModel.dailyMutableLiveData.observe(viewLifecycleOwner) {
            showDailyData(it)
        }
    }

    private fun showDailyData(it: List<DailyItem?>?) {
        dailyAdapter.changeData(it)
    }

    private fun showHourlyData(it: List<HourlyItem?>?) {
        hourlyAdapter.changeData(it)
    }


    private fun showCurrentWeatherData(it: Current?) {
        viewBinding.currentDateText.text = "${Constant.dateNow}"
        viewBinding.currentTimeText.text = "${Constant.timeNow}"
        viewBinding.currentTemperatureText.text = it?.temp.toString()
        viewBinding.currentHumidityText.text = it?.humidity.toString()
        viewBinding.currentWindText.text = it?.windSpeed.toString()
        viewBinding.currentPressureText.text = it?.pressure.toString()
        viewBinding.currentCloudText.text = it?.clouds.toString()
        viewBinding.currentDateText.text = "${Constant.dateNow}"
        viewBinding.currentTimeText.text = "${Constant.timeNow}"
        viewBinding.currentDescription.text = it?.weather?.get(0)?.description
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeToLiveData()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        fetchLocation()

    }


    private fun initViews() {
        hourlyAdapter = HourlyAdapter(null)
        dailyAdapter = DailyAdapter(null)
        hourlyRecyclerView = viewBinding.hourlyRecyclerview
        dailyRecyclerView = viewBinding.dailyRecyclerview
        hourlyRecyclerView.adapter = hourlyAdapter
        dailyRecyclerView.adapter = dailyAdapter
    }

    fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101
            )
        }
        task.addOnSuccessListener {
            if (it != null) {
                val addresses: List<Address>
                val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
                addresses = geocoder.getFromLocation(
                    it.latitude,
                    it.longitude,
                    1
                )

                //address from location using longitude and latitude
                val address: String = addresses[0].locality;
                viewBinding.getLocationText.text = address

                //load data from api from view model
                homeViewModel.fetchWeatherByLocation(
                    it.latitude.toInt(),
                    it.longitude.toInt(),
                    "metric"
                )
            }
        }
    }
    /*fun getImage(icon:String):String{
        this.icon =  icon
        return "http://openweathermap.org/img/w/${icon}.png"
    }*/



}

