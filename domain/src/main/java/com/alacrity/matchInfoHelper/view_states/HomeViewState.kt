package com.alacrity.matchInfoHelper.view_states

import com.alacrity.matchInfoHelper.entity.Example


sealed class MainViewState: BaseViewState {
    object Loading : MainViewState()
    object Refreshing: MainViewState()
    data class Error(val exception: Throwable? = null, val message: String = "") : MainViewState()
    object NoItems: MainViewState()

    data class FinishedLoading(val data: List<Example>) : MainViewState()
}