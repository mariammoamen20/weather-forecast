package com.example.weatherforecast.ui.home

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

class HomeFragment : Fragment(){
    lateinit var viewBinding : FragmentHomeBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fetchLocation()
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

                fetchWeatherByLocation(it.latitude.toInt(),it.longitude.toInt())

                //Toast.makeText(this, "${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun fetchWeatherByLocation(latitude:Int , longitude : Int){
        ApiManger.getApis().getWeather(latitude,longitude, Constant.API_KEY)
            .enqueue(object: Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    //address from api
                    viewBinding.timeZoneText.text= response.body()?.timezone.toString()
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })

    }

}