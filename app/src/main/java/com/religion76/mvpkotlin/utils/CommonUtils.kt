package com.religion76.mvpkotlin.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * Created by SunChao on 2019-08-14.
 */
object CommonUtils {

    private var windowWidth: Int? = null

    fun getWindowWidth(context: Context): Int {
        if (windowWidth == null){
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            windowManager.defaultDisplay.getSize(point)
            windowWidth = point.x
        }

        return windowWidth!!
    }
}