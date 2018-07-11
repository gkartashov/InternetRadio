package com.jg.internetradio.ui.fragment.player


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.jg.internetradio.R
import com.jg.internetradio.databinding.FragmentPlayerBinding
import com.jg.internetradio.entity.Station
import com.jg.internetradio.ui.fragment.OnPlayStart
import com.jg.internetradio.viewmodel.PlayerViewModel
import com.jg.internetradio.ui.misc.getRequestOptions


class PlayerFragment : Fragment(), OnPlayStart {
    private lateinit var binding: FragmentPlayerBinding

    override fun onStart(station: Station) {
        binding.playerViewModel?.addSource(station)
        binding.playerViewModel?.play()
    }

    companion object {
        fun newInstance() = PlayerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container,false)
        binding.playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)

        subscribeUI()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.playerImage.elevation = 10f
        }
        return binding.root
    }

    private fun subscribeUI() {
        val listener: (Boolean) -> Unit = {
            if (it) {
                binding.playerViewModel?.stop()
                binding.playerPlayButton.setImageResource(R.drawable.ic_play)
            } else {
                binding.playerViewModel?.play()
                binding.playerPlayButton.setImageResource(R.drawable.ic_pause)
            }
        }

        binding.playerViewModel?.observeToStation(this, Observer { t ->
            if (t != null) {
                binding.playerStationTitle.text = t.name
                binding.playerPlayButton.setImageResource(R.drawable.ic_pause)
                binding.playerCategoryTitle.text = binding.playerViewModel?.categoriesToString()
                binding.playerPlayButton.setOnClickListener { listener.invoke(binding.playerViewModel?.isPlaying!!) }

                Glide
                        .with(binding.root)
                        .setDefaultRequestOptions(getRequestOptions(ContextCompat.getDrawable(activity?.applicationContext!!,R.drawable.ic_note)))
                        .load(t.image?.url)
                        .into(binding.playerImage)
            } else {
                binding.playerCategoryTitle.text = resources.getString(R.string.defaultCategoryTitle)
                binding.playerStationTitle.text = resources.getString(R.string.defaultStationTitle)
                binding.playerImage.setImageResource(R.drawable.ic_note)
            }
        })
    }
}
