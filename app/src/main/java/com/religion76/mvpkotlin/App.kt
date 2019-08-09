package com.religion76.mvpkotlin

import android.app.Application
import timber.log.Timber

/**
 * Created by SunChao on 2019-07-19.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}