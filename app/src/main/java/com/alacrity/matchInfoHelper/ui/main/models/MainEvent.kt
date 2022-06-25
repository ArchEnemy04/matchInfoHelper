package com.alacrity.matchInfoHelper.ui.main.models

import com.alacrity.matchInfoHelper.BaseEvent
import com.alacrity.matchInfoHelper.entity.MatchInfo
import com.alacrity.matchInfoHelper.ui.main.MainViewModel

sealed class MainEvent : BaseEvent {

    object MoveToMainPage : MainEvent()

    object EnterScreen : MainEvent()

    object CheckNetwork : MainEvent()

    data class EnterMatchDetails(val matchInfo: MatchInfo) : MainEvent()

    data class EnterOddsTab(val matchInfo: MatchInfo): MainEvent()

}

fun MainViewModel.moveToMainPage() {
    obtainEvent(MainEvent.MoveToMainPage)
}

fun MainViewModel.enterScreen() {
    obtainEvent(MainEvent.EnterScreen)
}

fun MainViewModel.checkNetwork() {
    obtainEvent(MainEvent.CheckNetwork)
}

fun MainViewModel.moveToMatchDetails(matchInfo: MatchInfo) {
    obtainEvent(MainEvent.EnterMatchDetails(matchInfo))
}

fun MainViewModel.showOddsTab(matchInfo: MatchInfo) {
    obtainEvent(MainEvent.EnterOddsTab(matchInfo))
}
