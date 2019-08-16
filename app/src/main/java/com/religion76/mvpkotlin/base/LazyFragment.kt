package com.religion76.mvpkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Created by SunChao on 2019-08-09.
 */
abstract class LazyFragment : Fragment() {

    private var isInit = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("${javaClass.simpleName} onAttach_")
    }

    override fun onResume() {
        super.onResume()
        if (!isInit) {
            onLazyInit()
            isInit = true
            Timber.d("${javaClass.simpleName} onLazyInit_")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("${javaClass.simpleName} onViewCreated_")
        isInit = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("${javaClass.simpleName} onDestroyView_")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("${javaClass.simpleName} onDetach_")
    }

    abstract fun onLazyInit()
}