package com.alacrity.matchInfoHelper.ui.main.models

import com.alacrity.matchInfoHelper.BaseEvent
import com.alacrity.matchInfoHelper.ui.main.MainViewModel

sealed class MainEvent: BaseEvent {

    object EnterScreen : MainEvent()

}

fun MainViewModel.enterScreen() {
    obtainEvent(MainEvent.EnterScreen)
}