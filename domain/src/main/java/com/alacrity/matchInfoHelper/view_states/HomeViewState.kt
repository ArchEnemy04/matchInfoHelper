package com.alacrity.matchInfoHelper.view_states

import com.alacrity.matchInfoHelper.entity.MatchInfo


sealed class MainViewState : BaseViewState {
    object Loading : MainViewState()
    object Refreshing : MainViewState()
    data class Error(val exception: Throwable? = null, val message: String = "") : MainViewState()
    object NoItems : MainViewState()
    object NoNetworkConnection : MainViewState()

    data class MatchDetails(val info: MatchInfo) : MainViewState()
    data class FinishedLoading(val data: List<MatchInfo>) : MainViewState()
    data class OddsTab(val matchInfo: MatchInfo) : MainViewState()
}