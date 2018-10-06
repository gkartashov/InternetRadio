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
    companion object {
        fun newInstance() = RootFragment()
    }

    override var subject: PublishSubject<Any> = PublishSubject.create()

    private val shouldShowBackButton: Boolean
        get() = currentState == TransitionStates.TO_STATIONS

    private var currentState = TransitionStates.INIT_ROOT
    private val currentTransitionStateDescription = "CURRENT_TRANSITION_STATE"

    private val backActionListener = View.OnClickListener {
        currentState = TransitionStates.BACK
        publishUpdatedState()
        currentState = TransitionStates.INIT_ROOT
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val savedState = savedInstanceState?.getInt(currentTransitionStateDescription)
        currentState =  TransitionStates.fromInt(savedState)

        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_root, container, false)
        view.player_button.setOnClickListener { showPlayer() }
        view.back_button.apply {
            visibility = if (shouldShowBackButton) View.VISIBLE else View.GONE
            setOnClickListener(backActionListener)
        }

        if (savedInstanceState == null) {
            publishUpdatedState()
        }
        return view
    }

    override fun onDestroy() {
        subject.onComplete()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(currentTransitionStateDescription, currentState.stateNumber)
        super.onSaveInstanceState(outState)
    }

    private fun publishUpdatedState() = subject.onNext(currentState)

    fun showStationList(category: Category) {
        currentState = TransitionStates.TO_STATIONS
        showBackButton(true)
        subject.onNext(category)
    }

    fun showPlayer() {
        currentState = TransitionStates.TO_PLAYER
        publishUpdatedState()
    }

    fun showCategoryList(){
        currentState = TransitionStates.TO_CATEGORIES
        publishUpdatedState()
    }

    fun changeToolbarTitle(title: String) {
        appbar_layout.setExpanded(true)
        toolbar_title.text = title
    }

    fun showBackButton(isShow: Boolean) {
        back_button.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}
