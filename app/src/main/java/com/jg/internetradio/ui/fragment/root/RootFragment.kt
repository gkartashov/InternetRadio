package com.jg.internetradio.ui.fragment.root

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jg.internetradio.R
import com.jg.internetradio.entity.Category
import com.jg.internetradio.ui.fragment.OnFragmentChange
import com.jg.internetradio.ui.fragment.categorylist.CategoryListFragment
import com.jg.internetradio.ui.fragment.categorylist.OnCategoryClick
import com.jg.internetradio.ui.fragment.stationlist.StationListFragment
import kotlinx.android.synthetic.main.fragment_root.*

class RootFragment : Fragment(), OnFragmentChange, OnCategoryClick {
    companion object {
        fun newInstance() = RootFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_root, container, false)
        showCategoryList()
        return view
    }

    override fun onClick(category: Category) {
        activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                ?.replace(R.id.container, StationListFragment.newInstance(category, this), "StationListFragment")
                ?.addToBackStack("StationListFragment")
                ?.commit()
    }

    override fun onChange(title: String) {
        appbar_layout.setExpanded(true)
        toolbar.title = title
    }

    private fun showCategoryList() {
        activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                ?.replace(R.id.container, CategoryListFragment.newInstance(this,this))
                ?.commit()
    }
}
