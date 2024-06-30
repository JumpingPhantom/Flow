package com.jumpingphantom.flow

import android.app.Application
import com.jumpingphantom.flow.core.di.appModule
import com.jumpingphantom.flow.features.summary.di.summaryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@FlowApp)
            modules(appModule, summaryModule)
        }
    }
}