package com.alexius.shopy

import android.app.Application
import com.alexius.core.di.coreModule
import com.alexius.shopy.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ShopyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ShopyApp)
            modules(listOf(
                appModule,
                coreModule
            ))
        }
    }
}