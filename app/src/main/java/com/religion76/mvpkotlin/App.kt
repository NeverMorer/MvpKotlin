package com.religion76.mvpkotlin

import android.app.Application
import android.graphics.Bitmap
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by SunChao on 2019-07-19.
 */
class App : Application() {

    companion object {
        private lateinit var INSTANCE: App

        fun getInstance(): App {
            return INSTANCE
        }
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            modules(AppModules.appModule)
        }

        AppLaunch.initFresco(this)
    }

}