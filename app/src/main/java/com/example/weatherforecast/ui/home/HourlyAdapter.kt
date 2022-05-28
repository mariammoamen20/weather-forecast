package com.example.weatherforecast.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.Constant
import com.example.weatherforecast.R
import com.example.weatherforecast.data.api.HourlyItem
import com.example.weatherforecast.databinding.HourlyItemBinding

class HourlyAdapter(var hourlyList: List<HourlyItem?>?) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {
    class HourlyViewHolder(var itemBinding: HourlyItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(hourlyItem : HourlyItem? ){
            itemBinding.item = hourlyItem
            itemBinding.invalidateAll()
        }

        var hourText: TextView = itemBinding.hourText
        /*var hourIcon: ImageView = itemView.findViewById(R.id.hour_image)
        var hourTempText: TextView = itemView.findViewById(R.id.hour_temp)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val viewBinding : HourlyItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.hourly_item,parent,false)
        return HourlyViewHolder(viewBinding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = hourlyList?.get(position)
        holder.bind(item)
        if (item != null) {
            holder.hourText.text = item.dt?.let { Constant.unixTimeStampToDataTime(it) }
        }
        //  holder.dailyHour.text="${RetrofitClient.untixTimeStampToDataTime(dayily[position].date)}"
        /*holder.hourTempText.text = items?.temp.toString()
        *//*Glide.with(holder.itemView)
            .load(items?.weather?.get(0)?.icon)
            .into(holder.hourIcon)*/
    }

    override fun getItemCount(): Int {
        return hourlyList?.size ?: 0
    }

    fun changeData(list : List<HourlyItem?>?){
        this.hourlyList = list
        notifyDataSetChanged()
    }
}