package com.example.aston_intensiv_finale_projekt.app

import android.app.Application
import com.example.aston_intensiv_finale_projekt.di.AppComponent
import com.example.aston_intensiv_finale_projekt.di.AppModule
import com.example.aston_intensiv_finale_projekt.di.DaggerAppComponent


class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}