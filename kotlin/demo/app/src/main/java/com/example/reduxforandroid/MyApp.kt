package com.example.reduxforandroid

import android.app.Application
import android.content.Context

class MyApp : Application() {
    companion object {
        private lateinit var instance: MyApp

        fun getAppContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}