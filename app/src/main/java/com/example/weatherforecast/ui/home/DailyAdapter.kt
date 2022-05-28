package com.example.weatherforecast.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.Constant
import com.example.weatherforecast.R
import com.example.weatherforecast.data.api.DailyItem
import com.example.weatherforecast.databinding.DailyItemBinding
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter(var dailyList: List<DailyItem?>?) :
    RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {
    class DailyViewHolder(var itemBinding: DailyItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dailyItem: DailyItem?) {
            itemBinding.item = dailyItem
            itemBinding.invalidateAll()
        }

        var dayHourText: TextView = itemBinding.dayText
        /*var hourIcon: ImageView = itemView.findViewById(R.id.hour_image)
        var hourTempText: TextView = itemView.findViewById(R.id.hour_temp)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val viewBinding: DailyItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.daily_item,
                parent,
                false
            )
        return DailyViewHolder(viewBinding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val item = dailyList?.get(position)
        holder.bind(item)
        holder.dayHourText.text= SimpleDateFormat(" EEEE ", Locale.ENGLISH).format(Date(((item?.dt)?.toLong()!!*1000)))

        /*holder.hourTempText.text = items?.temp.toString()
        *//*Glide.with(holder.itemView)
            .load(items?.weather?.get(0)?.icon)
            .into(holder.hourIcon)*/
    }

    override fun getItemCount(): Int {
        return dailyList?.size ?: 0
    }

    fun changeData(list: List<DailyItem?>?) {
        this.dailyList = list
        notifyDataSetChanged()
    }
}