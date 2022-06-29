package com.alacrity.matchInfoHelper.di

import com.alacrity.matchInfoHelper.App
import com.alacrity.matchInfoHelper.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ApiModule::class, CoroutineModule::class, NetworkModule::class, UseCaseModule::class])
interface ApiComponent {
    fun inject(activity: MainActivity)

    fun inject(app: App)
}