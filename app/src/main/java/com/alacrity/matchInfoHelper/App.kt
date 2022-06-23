package com.alacrity.matchInfoHelper

import android.app.Application
import com.alacrity.matchInfoHelper.di.ApiComponent
import com.alacrity.matchInfoHelper.di.ApiModule
import com.alacrity.matchInfoHelper.di.AppModule
import com.alacrity.matchInfoHelper.di.DaggerApiComponent
import timber.log.Timber
import javax.inject.Inject

class App @Inject constructor(): Application() {

    companion object {

        lateinit var appComponent: ApiComponent

        /*var detailComponent: DetailComponent? = null
            get() {
                if (field == null) field = appComponent.provideDetailComponent()
                return field
            }


        fun clearDetailComponent() {
            detailComponent = null
        }*/
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent = DaggerApiComponent
            .builder()
            .apiModule(ApiModule("https://gorest.co.in/public/v2/users/1/"))
            .appModule(AppModule(this, this))
            .build()
            .apply { inject(this@App) }
    }
}