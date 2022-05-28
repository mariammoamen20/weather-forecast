package com.example.weatherforecast.ui.home

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.Constant
import com.example.weatherforecast.R
import com.example.weatherforecast.data.api.ApiManger
import com.example.weatherforecast.data.api.Response
import com.example.weatherforecast.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class HomeFragment : Fragment() {
    lateinit var viewBinding: FragmentHomeBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var hourlyRecyclerView: RecyclerView
    lateinit var dailyRecyclerView: RecyclerView
    lateinit var hourlyAdapter: HourlyAdapter
    lateinit var dailyAdapter: DailyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
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

    private fun fetchLocation() {
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

                fetchWeatherByLocation(it.latitude.toInt(), it.longitude.toInt(), "metric")

                //Toast.makeText(this, "${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    /*fun getImage(icon:String):String{
        this.icon =  icon
        return "http://openweathermap.org/img/w/${icon}.png"
    }*/

    fun fetchWeatherByLocation(latitude: Int, longitude: Int, units: String) {
        ApiManger.getApis().getWeather(latitude, longitude, Constant.API_KEY, units = units)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    //address from api
                    viewBinding.timeZoneText.text = response.body()?.timezone.toString()
                    viewBinding.currentTemperatureText.text =
                        response.body()?.current?.temp.toString()
                    viewBinding.currentHumidityText.text =
                        response.body()?.current?.humidity.toString()
                    viewBinding.currentWindText.text =
                        response.body()?.current?.windSpeed.toString()
                    viewBinding.currentPressureText.text =
                        response.body()?.current?.pressure.toString()
                    viewBinding.currentCloudText.text = response.body()?.current?.clouds.toString()
                    viewBinding.currentDateText.text = "${Constant.dateNow}"
                    viewBinding.currentTimeText.text = "${Constant.timeNow}"
                    viewBinding.currentDescription.text = response.body()?.current?.weather?.get(0)?.description
                    hourlyAdapter.changeData(response.body()?.hourly)
                    dailyAdapter.changeData(response.body()?.daily)
//

               }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })

    }

//    private fun ImageView.setCurrentIconResource(img: String?) {
//
//        when (img) {
//            "01d" -> AppCompatResources.getDrawable(context,R.drawable._01d)
//            "01n" -> AppCompatResources.getDrawable(context,R.drawable._01n)
//            "02d" -> AppCompatResources.getDrawable(context,R.drawable._02d)
//            "02n" -> AppCompatResources.getDrawable(context,R.drawable._02n)
//            "03d", "03n" ->AppCompatResources.getDrawable(context,R.drawable._03)
//            "04d", "04n" -> AppCompatResources.getDrawable(context,R.drawable._04)
//            "09d", "09n" ->AppCompatResources.getDrawable(context,R.drawable._09)
//            "10d" -> AppCompatResources.getDrawable(context,R.drawable._10d)
//            "10n" -> AppCompatResources.getDrawable(context,R.drawable._10n)
//            "11d", "11n" -> AppCompatResources.getDrawable(context,R.drawable._11)
//            "13d", "13n" -> AppCompatResources.getDrawable(context,R.drawable._13)
//            "50d", "50n" ->AppCompatResources.getDrawable(context,R.drawable._50)
//            else -> AppCompatResources.getDrawable(context,R.drawable.ic_broken_image)
//        }
//    }


}

