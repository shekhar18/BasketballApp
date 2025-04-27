package com.mahadiks.basketballapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BasketBallApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}