package com.jg.internetradio.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager

import com.jg.internetradio.R
import com.jg.internetradio.entity.Station
import com.jg.internetradio.ui.fragment.OnPlayStart
import com.jg.internetradio.ui.fragment.stationlist.OnStationClick
import com.jg.internetradio.ui.misc.PagerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), OnStationClick{
    private val pagerViewAdapter = PagerViewAdapter(supportFragmentManager)
    private lateinit var playListener: OnPlayStart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity_pager.adapter = pagerViewAdapter
        main_activity_pager.setPageTransformer(false, customTransformer())
    }

    private fun customTransformer() = ViewPager.PageTransformer { page, position ->
        page.translationX = page.width * if (position <= -1.0f || position >= 1.0f || position == 0.0f) position else -position
        page.alpha = when {
            position <= -1.0f || position >= 1.0f -> 0.0f
            position == 0.0f -> 1.0f
            else -> 1.0f - Math.abs(position)
        }
    }

    override fun onClick(station: Station) {
        main_activity_pager.currentItem = 1
        playListener = pagerViewAdapter.playerFragment
        playListener.onStart(station)

    }

}