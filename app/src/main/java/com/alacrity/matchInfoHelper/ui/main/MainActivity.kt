package com.alacrity.matchInfoHelper.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.alacrity.matchInfoHelper.App
import com.alacrity.matchInfoHelper.MatchInfoHelperApp
import javax.inject.Inject

class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContent {
            MatchInfoHelperApp(context = this, homeViewModel = mainViewModel)
        }
    }

}