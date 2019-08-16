package com.religion76.mvpkotlin.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Created by SunChao
 * on 2019-08-09.
 */
abstract class MultiSubFragment : Fragment(), MultiFragmentContainer {

    private lateinit var multiFragmentDelegate: MultiFragmentDelegate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSubFragments()
    }

    private fun initSubFragments() {
        multiFragmentDelegate =
            MultiFragmentDelegateImpl(getFragmentContainerId(), childFragmentManager, initShowIndex)
        multiFragmentDelegate.initFragments(fragmentPacks)
    }

    override fun showHideFragments(showIndex: Int) {
        multiFragmentDelegate.showHideFragments(showIndex)
    }
}