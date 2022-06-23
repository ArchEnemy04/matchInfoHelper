package com.alacrity.matchInfoHelper.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alacrity.matchInfoHelper.BaseEvent
import com.alacrity.matchInfoHelper.EventHandler
import com.alacrity.matchInfoHelper.view_states.BaseViewState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
abstract class BaseViewModel<T : BaseEvent, V : BaseViewState>(defaultViewState: V) : ViewModel(),
    EventHandler<T> {

    protected val _viewState: MutableStateFlow<V> = MutableStateFlow(defaultViewState)
    //public inline fun <R, T> Result<T>.fold

    protected fun <T> launch(
        dispatcher: CoroutineDispatcher,
        logError: String? = null,
        logSuccess: String? = null,
        onSuccess: ((value: T) -> Unit)? = null,
        onFailure: ((exception: Throwable) -> Unit)? = null,
        block: suspend () -> T
    ) {
        viewModelScope.launch(dispatcher) {
            safeCall(logSuccess, logError) {
                block()
            }.fold(
                onSuccess = { onSuccess?.invoke(it) },
                onFailure = { onFailure?.invoke(it) }
            )
        }
    }

    protected suspend fun <T> safeCall(
        successLogs: String? = null,
        errorLogs: String? = null,
        call: suspend () -> T
    ): Result<T> {
        return try {
            val result = call.invoke()
            Timber.d(successLogs)
            Result.success(result)
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            Timber.d("$errorLogs with $e")
            Result.failure(e)
        }
    }

    protected fun BaseViewState.logReduce() {
        Timber.d("Reduce $this")
    }

    protected fun BaseEvent.logEvent() {
        Timber.d("Event $this")
    }

}