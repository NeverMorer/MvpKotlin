package com.religion76.mvpkotlin

import android.app.Application
import android.graphics.Bitmap
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by SunChao on 2019-08-16.
 */
object AppLaunch {

    fun initFresco(application: Application) {
        if (!Fresco.hasBeenInitialized()) {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(30, TimeUnit.SECONDS)

            // Install an HTTP cache in the application cache directory.
            val cacheDir = File(application.cacheDir, "fresco")
            val cache = Cache(cacheDir, 20L * 1024L * 1024L)
            builder.cache(cache)

            val config = OkHttpImagePipelineConfigFactory
                .newBuilder(application, builder.build())
                .setDownsampleEnabled(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565).build()

            Fresco.initialize(application, config)
        }
    }
}