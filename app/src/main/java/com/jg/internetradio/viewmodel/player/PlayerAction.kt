package com.jg.internetradio.viewmodel.player

enum class PlayerAction(val stateId: Int) {
    PLAY(0),
    PAUSE(1),
    STOP(2),
    ERROR(3),
    SHOW_PLAYER(4),
    SHOW_STATIONS_SOURCE_LIST(5),
    CREATE(6);
}