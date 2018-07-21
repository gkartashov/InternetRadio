package com.jg.internetradio.ui.fragment.root

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jg.internetradio.R
import com.jg.internetradio.entity.Category
import com.jg.internetradio.ui.misc.TransitionHandler
import com.jg.internetradio.ui.misc.TransitionStates
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_root.*
import kotlinx.android.synthetic.main.fragment_root.view.*

class RootFragment : Fragment(), TransitionHandler {
    override var subject: PublishSubject<Any> = PublishSubject.create()

    companion object {
        fun newInstance() = RootFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_root, container, false)
        view.player_button.setOnClickListener { showPlayer() }
        if (savedInstanceState == null)
            subject.onNext(TransitionStates.INIT_ROOT)
        return view
    }

    override fun onDestroy() {
        subject.onComplete()
        super.onDestroy()
    }

    fun showStationList(category: Category) = subject.onNext(category)

    fun showPlayer() = subject.onNext(TransitionStates.TO_PLAYER)

    fun showCategoryList() = subject.onNext(TransitionStates.TO_CATEGORIES)

    fun changeToolbarTitle(title: String) {
        appbar_layout.setExpanded(true)
        toolbar_title.text = title
    }
}
