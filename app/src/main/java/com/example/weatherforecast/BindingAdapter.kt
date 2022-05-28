package com.example.weatherforecast

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("weatherIcon")
fun bindIcon(imgView: ImageView, img: String?) {
    var show: Drawable?
    img?.let {
        show = when (img) {
            "01d" -> AppCompatResources.getDrawable(imgView.context, R.drawable._01d)
            "01n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._01n)
            "02d" -> AppCompatResources.getDrawable(imgView.context, R.drawable._02d)
            "02n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._02n)
            "03d", "03n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._03)
            "04d", "04n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._04)
            "09d", "09n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._09)
            "10d" -> AppCompatResources.getDrawable(imgView.context, R.drawable._10d)
            "10n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._10n)
            "11d", "11n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._11)
            "13d", "13n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._13)
            "50d", "50n" -> AppCompatResources.getDrawable(imgView.context, R.drawable._50)
            else -> AppCompatResources.getDrawable(imgView.context, R.drawable.ic_broken_image)
        }
        imgView.setImageDrawable(show)
    }
}