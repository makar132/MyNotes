package com.example.mynotes

import android.app.Application
import com.example.mynotes.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotesApplication)
            modules(appModules)
        }
    }
}