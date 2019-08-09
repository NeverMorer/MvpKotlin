package com.religion76.mvpkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.religion76.mvpkotlin.base.LazyFragment

/**
 * Created by SunChao on 2019-08-09.
 */
class DiscoveryFragment : LazyFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onLazyInit() {

    }

}