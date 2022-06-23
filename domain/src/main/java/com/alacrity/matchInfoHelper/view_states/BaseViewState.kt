package com.alacrity.matchInfoHelper.view_states

sealed interface BaseViewState {



    fun getBaseState(): BaseViewState = Loading

    companion object {
        object Loading : BaseViewState
    }
}