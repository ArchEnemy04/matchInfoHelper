package com.alacrity.matchInfoHelper.ui.main

import com.alacrity.matchInfoHelper.Repository
import com.alacrity.matchInfoHelper.ui.main.models.MainEvent
import com.alacrity.matchInfoHelper.util.BaseViewModel
import com.alacrity.matchInfoHelper.view_states.MainViewState
import com.alacrity.matchInfoHelper.view_states.MainViewState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<MainEvent, MainViewState>(Loading) {

    val viewState: StateFlow<MainViewState> = _viewState

    override fun obtainEvent(event: MainEvent) {
        when (val currentState = _viewState.value) {
            is Loading -> currentState.reduce(event)
            is Error -> currentState.reduce()
            is FinishedLoading -> currentState.reduce()
            is NoItems -> currentState.reduce()
            is Refreshing -> currentState.reduce()
        }
    }

    private fun Loading.reduce(event: MainEvent) {
        logReduce()
        when(event) {
            MainEvent.EnterScreen -> {
                loadData()
            }
            else -> Unit
        }
    }

    private fun Error.reduce() {
        logReduce()

    }

    private fun FinishedLoading.reduce() {
        logReduce()

    }

    private fun NoItems.reduce() {
        logReduce()

    }

    private fun Refreshing.reduce() {
        logReduce()

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
            },
            onFailure = {

            }
        ) {
            repository.getData()
        }
    }


}