package com.alacrity.matchInfoHelper.ui.main

import androidx.lifecycle.viewModelScope
import com.alacrity.matchInfoHelper.NetworkUtil
import com.alacrity.matchInfoHelper.Repository
import com.alacrity.matchInfoHelper.entity.MatchInfo
import com.alacrity.matchInfoHelper.ui.main.models.MainEvent
import com.alacrity.matchInfoHelper.util.BaseViewModel
import com.alacrity.matchInfoHelper.view_states.MainViewState
import com.alacrity.matchInfoHelper.view_states.MainViewState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            is Error -> currentState.reduce()
            is FinishedLoading -> currentState.reduce(event)
            is NoItems -> currentState.reduce()
            is Refreshing -> currentState.reduce()
            is NoNetworkConnection -> currentState.reduce(event)
            is MatchDetails -> currentState.reduce(event)
        }
    }

    private fun Loading.reduce(event: MainEvent) {
        logReduce()
        when (event) {
            MainEvent.EnterScreen -> {
                event.logEvent()
                checkNetworkAndChangeViewState()
                subscribeToNetworkChanges()
            }
            else -> Unit
        }
    }

    private fun Error.reduce() {
        logReduce()

    }

    private fun FinishedLoading.reduce(event: MainEvent) {
        logReduce()
        when (event) {
            is MainEvent.EnterMatchDetails -> {
                event.logEvent()
                _viewState.value = MatchDetails(event.matchInfo)
            }
            else -> Unit
        }
    }

    private fun NoItems.reduce() {
        logReduce()

    }

    private fun Refreshing.reduce() {
        logReduce()

    }

    private fun NoNetworkConnection.reduce(event: MainEvent) {
        logReduce()
        when (event) {
            is MainEvent.CheckNetwork -> {
                event.logEvent()
                checkNetworkAndChangeViewState()
            }
            else -> Unit
        }
    }

    private fun MatchDetails.reduce(event: MainEvent) {
        logReduce()
        when (event) {
            is MainEvent.MoveToMainPage -> {
                event.logEvent()
                _viewState.value = FinishedLoading(dataFlow)
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