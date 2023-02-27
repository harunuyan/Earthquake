package com.volie.earthquake.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.volie.earthquake.R
import com.volie.earthquake.databinding.AdapterItemEarthquakeBinding
import com.volie.earthquake.model.EarthquakeModel

class EarthquakeAdapter : RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder>() {

    private val items: MutableList<EarthquakeModel> = mutableListOf()

    inner class EarthquakeViewHolder(private val binding: AdapterItemEarthquakeBinding) :
        ViewHolder(binding.root) {
        fun bindData(position: Int) {
            with(binding) {
                txtMag.text = items[position].magnitudeText
                txtName.text = items[position].name
                txtDate.text = items[position].date
                txtTime.text = items[position].time
                cardMag.setBackgroundColor(items[position].magnitudeColor)
            }

            val backGround = R.drawable.item_background as ViewGroup
            backGround.setBackgroundColor(items[position].magnitudeColorLight)
            //TODO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthquakeViewHolder {
        val binding =
            AdapterItemEarthquakeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EarthquakeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EarthquakeViewHolder, position: Int) {
        holder.bindData(position)
    }

    fun setItems(items: List<EarthquakeModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}