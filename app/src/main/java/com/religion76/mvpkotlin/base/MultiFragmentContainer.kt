package com.religion76.mvpkotlin.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * Created by SunChao on 2019-08-15.
 */
interface MultiFragmentContainer {

    val fragmentPacks: Array<FragmentPack<out Fragment>>

    val initShowIndex: Int

    @IdRes fun getFragmentContainerId(): Int

    fun showHideFragments(showIndex: Int)
}