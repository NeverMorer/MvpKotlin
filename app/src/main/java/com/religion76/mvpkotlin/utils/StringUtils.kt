package com.religion76.mvpkotlin.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import com.religion76.mvpkotlin.App

/**
 * Created by SunChao on 2019-08-13.
 */
object StringUtils {

    private fun getRes(): Resources {
        return App.getInstance().resources
    }

    fun getString(@StringRes stringRes: Int): String {
        return getRes().getString(stringRes)
    }
}