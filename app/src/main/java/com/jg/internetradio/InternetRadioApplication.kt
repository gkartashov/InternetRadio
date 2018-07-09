package com.jg.internetradio

import android.app.Application
import com.jg.internetradio.repository.RadioRepository

class InternetRadioApplication : Application() {
    fun getRadioRepository(): RadioRepository {
        RadioRepository.INSTANCE.context = this
        return RadioRepository.INSTANCE
    }
}