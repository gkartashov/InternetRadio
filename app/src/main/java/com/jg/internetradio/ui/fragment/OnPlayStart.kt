package com.jg.internetradio.ui.fragment

import com.jg.internetradio.entity.Station

interface OnPlayStart {
    fun onStart(station: Station)
}