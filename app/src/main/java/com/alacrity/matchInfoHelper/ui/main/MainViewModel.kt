package com.alacrity.matchInfoHelper.ui.main

import com.alacrity.matchInfoHelper.NetworkUtil
import com.alacrity.matchInfoHelper.Repository
import com.alacrity.matchInfoHelper.entity.MatchInfo
import com.alacrity.matchInfoHelper.ui.main.models.MainEvent
import com.alacrity.matchInfoHelper.util.BaseViewModel
import com.alacrity.matchInfoHelper.view_states.MainViewState
import com.alacrity.matchInfoHelper.view_states.MainViewState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val networkUtil: NetworkUtil
) : BaseViewModel<MainEvent, MainViewState>(Loading) {

    val viewState: StateFlow<MainViewState> = _viewState

    private val dataFlow: MutableList<MatchInfo> = mutableListOf()

    override fun obtainEvent(event: MainEvent) {
        when (val currentState = _viewState.value) {
            is Loading -> currentState.reduce(event)
            is Error -> currentState.reduce(event)
            is FinishedLoading -> currentState.reduce(event)
            is NoItems -> currentState.reduce(event)
            is Refreshing -> currentState.reduce(event)
            is NoNetworkConnection -> currentState.reduce(event)
            is MatchDetails -> currentState.reduce(event)
            is OddsTab -> currentState.reduce(event)
        }
    }

    private fun Loading.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            MainEvent.EnterScreen -> {
                checkNetworkAndChangeViewState()
                subscribeToNetworkChanges()
            }
            else -> Unit
        }
    }

    private fun Error.reduce(event: MainEvent) {
        logReduce(event)

    }

    private fun FinishedLoading.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.EnterMatchDetails -> {
                _viewState.value = MatchDetails(event.matchInfo)
            }
            else -> Unit
        }
    }

    private fun NoItems.reduce(event: MainEvent) {
        logReduce(event)

    }

    private fun Refreshing.reduce(event: MainEvent) {
        logReduce(event)

    }

    private fun NoNetworkConnection.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.CheckNetwork -> {
                checkNetworkAndChangeViewState()
            }
            else -> Unit
        }
    }

    private fun MatchDetails.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.MoveToMainPage -> {
                _viewState.value = FinishedLoading(dataFlow)
            }
            is MainEvent.EnterOddsTab -> {
                _viewState.value = OddsTab(event.matchInfo)
            }
            else -> Unit
        }
    }

    private fun OddsTab.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.EnterMatchDetails -> {
                _viewState.value = MatchDetails(event.matchInfo)
            }
            else -> Unit
        }
    }

    private fun loadData() {
        launch(
            dispatcher = Dispatchers.IO,
            logError = "Error getting data",
            logSuccess = "Success getting data",
            onSuccess = {
                it.forEach { data ->
                    Timber.d("data: $data")
                }
                _viewState.value = FinishedLoading(it)
                dataFlow.addAll(it)
            },
            onFailure = {
                _viewState.value = Error(it)
            }
        ) {
            repository.getData()
        }
    }

    private fun subscribeToNetworkChanges() {
        networkUtil.subscribeToConnectionChange(this) { isConnected ->
            if (!isConnected) {
                _viewState.value = NoNetworkConnection
            } else {
                loadData()
                _viewState.value = Loading
            }
        }
    }

    private fun checkNetworkAndChangeViewState() {
        if (networkUtil.isOnline()) {
            loadData()
            _viewState.value = Loading
        } else {
            _viewState.value = NoNetworkConnection
        }
    }


}