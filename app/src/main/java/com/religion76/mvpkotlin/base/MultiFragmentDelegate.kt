package com.religion76.mvpkotlin.base

import androidx.fragment.app.Fragment

/**
 * Created by SunChao on 2019-08-15.
 */
interface MultiFragmentDelegate {

    fun initFragments(fragmentPacks: Array<FragmentPack<out Fragment>>)

    fun showHideFragments(showIndex: Int)
}