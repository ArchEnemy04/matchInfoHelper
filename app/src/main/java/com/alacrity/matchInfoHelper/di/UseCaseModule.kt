package com.alacrity.matchInfoHelper.di

import com.alacrity.matchInfoHelper.use_cases.GetMatchesFromApiUseCase
import com.alacrity.matchInfoHelper.use_cases.GetMatchesFromApiUseCaseImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface UseCaseModule {

    @Binds
    @Singleton
    fun bind(impl: GetMatchesFromApiUseCaseImpl): GetMatchesFromApiUseCase

}