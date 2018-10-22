package com.jg.internetradio.ui.misc

enum class TransitionStates(val stateNumber: Int) {
    INIT_ROOT(0),
    TO_CATEGORIES(1),
    TO_STATIONS(2),
    TO_PLAYER(3),
    BACK(4);

    companion object {
        private val map = TransitionStates.values().associateBy(TransitionStates::stateNumber)

        fun fromInt(type: Int?): TransitionStates = map[type] ?: INIT_ROOT
    }
}