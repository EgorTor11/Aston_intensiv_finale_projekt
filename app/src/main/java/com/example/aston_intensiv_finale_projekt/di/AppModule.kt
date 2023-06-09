package com.example.aston_intensiv_finale_projekt.di

import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext(): Context {
        return context
    }
}