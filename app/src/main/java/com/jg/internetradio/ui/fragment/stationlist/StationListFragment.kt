package com.jg.internetradio.ui.fragment.stationlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jg.internetradio.R
import com.jg.internetradio.databinding.FragmentStationListBinding
import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.Station
import com.jg.internetradio.ui.fragment.OnFragmentChange
import com.jg.internetradio.ui.fragment.stationlist.recyclerview.StationListAdapter
import com.jg.internetradio.ui.misc.marginDecorator
import com.jg.internetradio.viewmodel.StationListViewModel
import io.reactivex.subjects.PublishSubject

class StationListFragment : Fragment(), OnStationClick {
    override fun onClick(station: Station) {
        onStationClickedSubject.onNext(station)
    }

    private lateinit var binding: FragmentStationListBinding
    private lateinit var stationListAdapter: StationListAdapter

    val onStationClickedSubject = PublishSubject.create<Station>()

    companion object {
        lateinit var listener: OnFragmentChange
        fun newInstance(category: Category, listener: OnFragmentChange) : StationListFragment {

            this.listener = listener
            val fragment = StationListFragment()
            val args = Bundle()
            args.putSerializable("Category", category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val category = arguments?.getSerializable("Category") as Category
        val factory = StationListViewModel.Factory(activity?.application!!, category)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_station_list, container,false)
        binding.stationListViewModel = ViewModelProviders.of(this, factory).get(StationListViewModel::class.java)

        stationListAdapter = StationListAdapter(activity?.applicationContext, this)

        binding.stationListRecyclerView.adapter = stationListAdapter
        binding.stationListRecyclerView.addItemDecoration(marginDecorator(context))

        listener.onChange("${category.title} stations")

        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        binding.stationListViewModel?.stationList?.observe(this, Observer<List<Station>> { t ->
            if (t != null) {
                stationListAdapter.stations = t
                stationListAdapter.notifyDataSetChanged()
            }
        })
    }



}
