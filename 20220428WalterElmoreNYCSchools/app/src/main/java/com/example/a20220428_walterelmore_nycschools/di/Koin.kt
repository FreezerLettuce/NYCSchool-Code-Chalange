package com.example.a20220428_walterelmore_nycschools.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Koin : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@Koin)
            androidLogger(Level.ERROR)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    apiModule,
                    retrofitModule
                )
            )
        }
    }
}