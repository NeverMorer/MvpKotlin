package com.religion76.mvpkotlin.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.religion76.mvpkotlin.R
import com.religion76.mvpkotlin.base.*

/**
 * Created by SunChao on 2019-08-09.
 */
class Discovery : LazyFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onLazyInit() {
        showMessage("lazy init")
    }
}