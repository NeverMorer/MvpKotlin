package com.religion76.navigationdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Created by SunChao on 2019-07-19.
 */
open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate: ${javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: ${javaClass.simpleName}")
    }
}