package com.dicoding.pokedex

import android.app.Application
import com.dicoding.pokedex.core.di.databaseModule
import com.dicoding.pokedex.core.di.networkModule
import com.dicoding.pokedex.core.di.repositoryModule
import com.dicoding.pokedex.di.useCaseModule
import com.dicoding.pokedex.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
