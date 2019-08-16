package com.religion76.mvpkotlin.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * Created by SunChao
 * on 2019-08-09.
 */
abstract class MultiFragmentActivity : FragmentActivity(), MultiFragmentContainer {

    private lateinit var multiFragmentDelegate: MultiFragmentDelegate

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initFragments()
    }

    private fun initFragments() {
        multiFragmentDelegate =
            MultiFragmentDelegateImpl(getFragmentContainerId(), supportFragmentManager, initShowIndex)
        multiFragmentDelegate.initFragments(fragmentPacks)
    }

    override fun showHideFragments(showIndex: Int) {
        multiFragmentDelegate.showHideFragments(showIndex)
    }

}