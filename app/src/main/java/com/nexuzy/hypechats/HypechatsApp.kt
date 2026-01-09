package com.nexuzy.hypechats

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HypechatsApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
