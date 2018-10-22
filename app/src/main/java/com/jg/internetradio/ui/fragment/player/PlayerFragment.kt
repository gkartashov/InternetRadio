package com.jg.internetradio.ui.fragment.player


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jg.internetradio.R
import com.jg.internetradio.databinding.FragmentPlayerBinding
import com.jg.internetradio.entity.Station
import com.jg.internetradio.viewmodel.PlayerViewModel

class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false)
        binding.playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)

        lifecycle.addObserver(binding.playerViewModel as PlayerViewModel)
        binding.setLifecycleOwner(this)

        binding.playerStationListButton.setOnClickListener { toCategoryListAction.invoke() }
        binding.playerViewModel?.observeToStation(this, Observer {})

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.playerImage.elevation = 10f
        }

        return binding.root
    }

    fun play(station: Station): Unit = with(binding) {
        playerViewModel?.addSource(station)
        playerViewModel?.play()
    }

    companion object {
        lateinit var toCategoryListAction: () -> Unit

        fun newInstance(toCategoryListAction: () -> Unit): PlayerFragment {
            this.toCategoryListAction = toCategoryListAction
            return PlayerFragment()
        }
    }
}
