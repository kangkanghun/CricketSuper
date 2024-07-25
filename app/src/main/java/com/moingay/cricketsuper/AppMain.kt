package com.moingay.cricketsuper

import android.app.Application
import com.moingay.cricketsuper.di.dataStoreModule
import com.moingay.cricketsuper.di.dispatcherModule
import com.moingay.cricketsuper.di.networkModule
import com.moingay.cricketsuper.di.repositoryModule
import com.moingay.cricketsuper.di.useCaseModule
import com.moingay.cricketsuper.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppMain : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppMain)
            modules(
                dispatcherModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                dataStoreModule
            )
        }
    }
}