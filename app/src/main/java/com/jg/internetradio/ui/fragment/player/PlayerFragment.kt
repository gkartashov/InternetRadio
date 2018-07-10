package com.jg.internetradio.ui.fragment.player


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jg.internetradio.R
import com.jg.internetradio.entity.Station
import com.jg.internetradio.ui.fragment.OnPlayStart
import com.jg.internetradio.viewmodel.manager.PlayerManager

class PlayerFragment : Fragment(), OnPlayStart {
    var p: PlayerManager? = null
    override fun onStart(station: Station) {
        if (p == null)
            p = PlayerManager(activity?.applicationContext!!)
        p?.play(station.streams?.first()?.stream!!)
    }

    companion object {
        fun newInstance() = PlayerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }


}
