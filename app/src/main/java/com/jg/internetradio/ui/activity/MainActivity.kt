package com.jg.internetradio.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager

import com.jg.internetradio.R
import com.jg.internetradio.ui.fragment.stationlist.StationListFragment
import com.jg.internetradio.ui.misc.PagerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private val pagerViewAdapter = PagerViewAdapter(supportFragmentManager)
    private lateinit var stationListFragment: StationListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity_pager.adapter = pagerViewAdapter

        main_activity_pager.setPageTransformer(false, customTransformer())

        subscribeActivity()
    }

    override fun onBackPressed() {
        when {
            main_activity_pager.currentItem == 1 -> {
                if (supportFragmentManager.findFragmentByTag(StationListFragment::javaClass.name) == null)
                    supportFragmentManager.popBackStackImmediate()
                super.onBackPressed()
            }
            supportFragmentManager.findFragmentByTag(StationListFragment::javaClass.name) != null -> supportFragmentManager.popBackStackImmediate()
            else -> super.onBackPressed()
        }
    }

    private fun subscribeActivity() {
        pagerViewAdapter.rootFragment.playerSubject.subscribe { main_activity_pager.currentItem = 1 }

        pagerViewAdapter.playerFragment.playerSubject.subscribe {
            main_activity_pager.currentItem = 0
        }

        pagerViewAdapter.rootFragment.stationListFragmentSubject.subscribe {
            stationListFragment = it

            stationListFragment.onStationClickedSubject.subscribe {
                main_activity_pager.currentItem = 1
                pagerViewAdapter.playerFragment.play(it)
            }
        }
    }

    private fun customTransformer() = ViewPager.PageTransformer { page, position ->
        page.translationX = page.width * if (position <= -1.0f || position >= 1.0f || position == 0.0f) position else -position
        page.alpha = when {
            position <= -1.0f || position >= 1.0f -> 0.0f
            position == 0.0f -> 1.0f
            else -> 1.0f - Math.abs(position)
        }
    }
}