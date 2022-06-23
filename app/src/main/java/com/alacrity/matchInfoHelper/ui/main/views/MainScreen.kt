package com.alacrity.matchInfoHelper.ui.main.views

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.alacrity.matchInfoHelper.customShape
import com.alacrity.matchInfoHelper.ui.main.MainViewModel
import com.alacrity.matchInfoHelper.ui.main.models.checkNetwork
import com.alacrity.matchInfoHelper.ui.main.models.enterScreen
import com.alacrity.matchInfoHelper.ui.main.models.moveToMainPage
import com.alacrity.matchInfoHelper.ui.main.models.moveToMatchDetails
import com.alacrity.matchInfoHelper.util.getScreenSize
import com.alacrity.matchInfoHelper.view_states.MainViewState
import timber.log.Timber
import kotlin.system.exitProcess

@Composable
fun MainScreen(
    context: Context,
    viewModel: MainViewModel,
) {

    val state by viewModel.viewState.collectAsState()

    MainWrapper(viewModel = viewModel) {
        when (state) {
            MainViewState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LinearProgressIndicator()
                }
            }
            is MainViewState.NoItems -> {

            }
            is MainViewState.Error -> {
                /* ShowErrorView */
            }

            is MainViewState.Refreshing -> {

            }
            is MainViewState.FinishedLoading -> {
                ScreenWithItems((state as MainViewState.FinishedLoading).data) {
                    viewModel.moveToMatchDetails(it)
                }
            }
            is MainViewState.NoNetworkConnection -> {
                NoNetworkView { viewModel.checkNetwork() }
            }
            is MainViewState.MatchDetails -> {
                DetailMatchScreen((state as MainViewState.MatchDetails).info)
            }
            else -> Unit
        }

        val activity = (LocalContext.current as? Activity)

        BackHandler {
            Timber.d("onBackClicked, state: $state")
            if (state is MainViewState.MatchDetails) {
                Timber.d("onMoveToMain")
                viewModel.moveToMainPage()
            } else {
                Timber.d("onBackPressed")
                exitProcess(0)
            }
        }

        LaunchedEffect(key1 = state, block = {
            viewModel.enterScreen()
        })

    }
}

@Composable
fun MainWrapper(viewModel: MainViewModel, content: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    with(getScreenSize<Float>()) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                /*AppDrawer(
                    closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } },
                    width = (first / 5 - 20).dp,
                    refresh = { viewModel.obtainEvent(HomeEvent.OnRefreshClicked) }
                )*/
            },
            drawerShape = customShape(first, second),
        ) {
            content()
        }
    }
}