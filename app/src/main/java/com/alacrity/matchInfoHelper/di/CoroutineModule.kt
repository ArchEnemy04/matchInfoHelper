package com.alacrity.matchInfoHelper.di

import com.alacrity.matchInfoHelper.dispatchers.CoroutineDispatchers
import com.alacrity.matchInfoHelper.dispatchers.CoroutineDispatchersImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface CoroutineModule {

    @Binds
    @Singleton
    fun bindDispatchers(impl: CoroutineDispatchersImpl): CoroutineDispatchers

}