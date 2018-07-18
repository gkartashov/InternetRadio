package com.jg.internetradio.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager

import com.jg.internetradio.R
import com.jg.internetradio.entity.Category
import com.jg.internetradio.ui.fragment.categorylist.CategoryListFragment
import com.jg.internetradio.ui.fragment.stationlist.StationListFragment
import com.jg.internetradio.ui.misc.PagerViewAdapter
import com.jg.internetradio.ui.misc.TransitionStates
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private val pagerViewAdapter = PagerViewAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity_pager.adapter = pagerViewAdapter
        main_activity_pager.setPageTransformer(false, customTransformer())

        pagerViewAdapter.playerFragment

        pagerViewAdapter.rootFragment.subject.subscribe {
            when(it) {
                is TransitionStates -> {
                    when (it) {
                        TransitionStates.INIT_ROOT -> showCategoryList()
                        TransitionStates.TO_CATEGORIES -> main_activity_pager.currentItem = 0
                        TransitionStates.TO_PLAYER -> main_activity_pager.currentItem = 1
                        else -> throw NullPointerException("Wrong value!")
                    }
                }
                is Category -> showStationList(it)
            }
        }
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

    private fun showStationList(category: Category) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in,
                        R.anim.abc_fade_out,
                        R.anim.abc_fade_in,
                        R.anim.abc_fade_out)
                .replace(R.id.container, StationListFragment.newInstance(category,
                        pagerViewAdapter.rootFragment::showPlayer,
                        pagerViewAdapter.playerFragment::play), "StationListFragment")
                .addToBackStack("StationListFragment")
                .commit()
    }

    private fun showCategoryList() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in,
                        R.anim.abc_fade_out,
                        R.anim.abc_fade_in,
                        R.anim.abc_fade_out)
                .replace(R.id.container, CategoryListFragment.newInstance(pagerViewAdapter.rootFragment::changeToolbarTitle, pagerViewAdapter.rootFragment::showStationList))
                .commit()
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