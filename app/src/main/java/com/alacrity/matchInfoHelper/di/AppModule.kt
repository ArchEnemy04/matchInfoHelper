package com.alacrity.matchInfoHelper.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context, private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = context.applicationContext

    @Provides
    @Singleton
    fun provideResources(): Resources = context.resources

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return app
    }

}