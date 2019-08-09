package com.religion76.mvpkotlin.base

import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Created by SunChao on 2019-08-09.
 */
abstract class LazyFragment : Fragment() {

    private var isInit = false

    override fun onResume() {
        super.onResume()
        if (!isInit) {
            onLazyInit()
            isInit = true
            Timber.d("${javaClass.simpleName} onLazyInit_")
        }
    }

    abstract fun onLazyInit()
}