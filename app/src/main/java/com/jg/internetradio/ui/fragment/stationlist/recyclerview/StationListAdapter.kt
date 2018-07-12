package com.jg.internetradio.ui.fragment.stationlist.recyclerview

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jg.internetradio.R
import com.jg.internetradio.databinding.StationListItemBinding
import com.jg.internetradio.entity.Station
import com.jg.internetradio.ui.fragment.stationlist.OnStationClick
import com.jg.internetradio.ui.misc.circularProgressDrawable
import com.jg.internetradio.ui.misc.getRequestOptions

class StationListAdapter(private val context: Context?,
                         private val listener: OnStationClick,
                         var stations: List<Station> = emptyList()) : RecyclerView.Adapter<StationListAdapter.ViewHolder>() {
    class ViewHolder(val binding: StationListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context?) {
            Glide
                    .with(binding.root)
                    .setDefaultRequestOptions(getRequestOptions(circularProgressDrawable(context!!)))
                    .load(binding.station?.image?.thumb?.url?.trim())
                    .into(binding.stationThumb)
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: StationListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.station_list_item, parent, false)
        binding.callback = listener
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.station = stations[position]
        holder.bind(context)
    }

    override fun getItemId(position: Int) = stations[position].id.toLong()

    override fun getItemCount() = stations.size

}
