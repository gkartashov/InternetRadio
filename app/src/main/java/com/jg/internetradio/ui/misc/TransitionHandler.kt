package com.jg.internetradio.ui.misc

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface TransitionHandler {
    val subject: PublishSubject<Any>
}