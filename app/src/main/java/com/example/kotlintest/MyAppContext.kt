package com.example.kotlintest

import android.app.Application
import android.util.Log

class MyAppContext : Application() {


    override fun onCreate() {
        super.onCreate()
        Log.i("hltag","application init...")
    }
}