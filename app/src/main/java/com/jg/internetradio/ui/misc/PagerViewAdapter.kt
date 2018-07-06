package com.jg.internetradio.ui.misc

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jg.internetradio.ui.fragment.CategoryListFragment
import com.jg.internetradio.ui.fragment.PlayerFragment

class PagerViewAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int) = when(position) {
        0 -> CategoryListFragment.newInstance()
        1 -> PlayerFragment.newInstance()
        else -> Fragment()
    }

    override fun getCount() = 2
}