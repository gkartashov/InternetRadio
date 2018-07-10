package com.jg.internetradio.ui.misc

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jg.internetradio.ui.fragment.player.PlayerFragment
import com.jg.internetradio.ui.fragment.root.RootFragment

class PagerViewAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val playerFragment = PlayerFragment.newInstance()
    override fun getItem(position: Int) = when(position) {
        0 -> RootFragment.newInstance()
        1 -> playerFragment
        else -> Fragment()
    }

    override fun getCount() = 2
}