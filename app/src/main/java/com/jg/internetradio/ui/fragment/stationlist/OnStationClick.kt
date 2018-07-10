package com.jg.internetradio.ui.fragment.stationlist

import com.jg.internetradio.entity.Station

interface OnStationClick {
    fun onClick(station: Station)
}