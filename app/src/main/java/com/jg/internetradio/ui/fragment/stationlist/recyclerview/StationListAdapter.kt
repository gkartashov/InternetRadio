package com.jg.internetradio.ui.fragment.stationlist.recyclerview

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jg.internetradio.R
import com.jg.internetradio.databinding.StationListItemBinding
import com.jg.internetradio.entity.Station
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class StationListAdapter(var stations: List<Station> = emptyList(),
                         private val clickSubject: PublishSubject<Station> = PublishSubject.create(),
                         val clickObservable: Observable<Station> = clickSubject) : RecyclerView.Adapter<StationListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: StationListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.stationListCardView.setOnClickListener {
                clickSubject.onNext(binding.station ?: Station())
            }
            binding.executePendingBindings()
        }
    }

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
        holder.bind()
    }

    override fun getItemId(position: Int) = stations[position].id.toLong()

    override fun getItemCount() = stations.size

}
