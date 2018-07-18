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
import com.jg.internetradio.viewmodel.PlayerViewModel
import com.jg.internetradio.ui.misc.getRequestOptions

class PlayerFragment : Fragment() {
    companion object {
        lateinit var toCategoryListAction: () -> Unit
        fun newInstance(toCategoryListAction: () -> Unit): PlayerFragment {
            this.toCategoryListAction = toCategoryListAction
            return PlayerFragment()
        }
    }

    private lateinit var binding: FragmentPlayerBinding

    private val playButtonListener: (Boolean) -> Unit = {
        if (it) {
            binding.playerViewModel?.stop()
            binding.playerPlayButton.setBackgroundResource(R.drawable.ic_play)
        } else {
            binding.playerViewModel?.play()
            binding.playerPlayButton.setBackgroundResource(R.drawable.ic_pause)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container,false)
        binding.playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)

        setButtonListeners()
        initUI()
        subscribeUI()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.playerImage.elevation = 10f
        }

        return binding.root
    }

    private fun initUI(): Unit = with(binding) {
        playerPlayButton.isClickable = false
        playerStopButton.isClickable = false
        playerCategoryTitle.text = resources.getString(R.string.defaultCategoryTitle)
        playerStationTitle.text = resources.getString(R.string.defaultStationTitle)
        playerImage.setImageResource(R.drawable.default_image)
        playerPlayButton.setBackgroundResource(R.drawable.ic_play)
    }

    private fun setButtonListeners(): Unit = with(binding) {
        playerPlayButton.setOnClickListener { playButtonListener.invoke(playerViewModel?.isPlaying!!) }
        playerStopButton.setOnClickListener {
            playerViewModel?.stop()
            playerViewModel?.removeSource()
            playerPlayButton.isClickable = false
            playerStopButton.isClickable = false
        }
        playerStationListButton.setOnClickListener { toCategoryListAction.invoke() }
    }

    private fun subscribeUI() {
        val fragment = this

        with(binding) {
            playerViewModel?.observeToStation(fragment, Observer { t ->
                if (t != null) {
                    playerStopButton.isClickable = true
                    playerPlayButton.isClickable = true
                    playerStationTitle.text = t.name
                    playerPlayButton.setBackgroundResource(R.drawable.ic_pause)
                    playerCategoryTitle.text = playerViewModel?.categoriesToString()

                    Glide
                            .with(root)
                            .setDefaultRequestOptions(getRequestOptions(ContextCompat.getDrawable(activity?.applicationContext!!, R.drawable.default_image)))
                            .load(t.image?.url)
                            .into(playerImage)
                } else
                    initUI()
            })
        }
    }

    fun play(station: Station): Unit = with(binding) {
        playerViewModel?.addSource(station)
        playerViewModel?.play()
    }

}
