package com.jg.internetradio.ui.fragment.stationlist.recyclerview

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jg.internetradio.R
import com.jg.internetradio.databinding.StationListItemBinding
import com.jg.internetradio.entity.Station

class StationListAdapter(var stations: List<Station> = emptyList()) : RecyclerView.Adapter<StationListAdapter.ViewHolder>() {
    class ViewHolder(val binding: StationListItemBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: StationListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.station_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.station = stations[position]
    }

    override fun getItemId(position: Int) = stations[position].id.toLong()

    override fun getItemCount() = stations.size

}
