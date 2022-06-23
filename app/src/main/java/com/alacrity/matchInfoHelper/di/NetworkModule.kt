package com.alacrity.matchInfoHelper.di


import com.alacrity.matchInfoHelper.NetworkUtil
import com.alacrity.matchInfoHelper.Repository
import com.alacrity.matchInfoHelper.RepositoryImpl
import com.alacrity.matchInfoHelper.util.NetworkUtilImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Binds
    @Singleton
    fun bindNetworkUtil(impl: NetworkUtilImpl): NetworkUtil


 /*   @Binds
    @Singleton
    fun bindNewMessageReceivedUseCase(impl: NewMessageReceivedUseCaseImpl): NewMessageReceivedUseCase*/

    @Binds
    @Singleton
    fun bindRepository(impl: RepositoryImpl): Repository

}