package com.jg.internetradio.ui.misc

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.jg.internetradio.ui.fragment.player.PlayerFragment
import com.jg.internetradio.ui.fragment.root.RootFragment

class PagerViewAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    var rootFragment: RootFragment = RootFragment.newInstance()
    var playerFragment: PlayerFragment = PlayerFragment.newInstance(rootFragment::showCategoryList)

    override fun getItem(position: Int) = when(position) {
        0 -> rootFragment
        1 -> playerFragment
        else -> null
    }

    override fun getCount() = 2

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position)

        when (position) {
            0 -> {
                (fragment as RootFragment).subject = rootFragment.subject
                rootFragment = fragment
            }
            1 -> playerFragment = fragment as PlayerFragment
        }

        return fragment
    }
}