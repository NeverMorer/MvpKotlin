package com.religion76.mvpkotlin.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import timber.log.Timber

/**
 * Created by SunChao on 2019-08-15.
 */
class MultiFragmentDelegateImpl(
    @IdRes private val contentId: Int,
    private val fragmentManager: FragmentManager,
    private val initShowIndex: Int
) : MultiFragmentDelegate{

    private var currentShowFragmentIndex = -1

    private var fragments: Array<Fragment>? = null

    override fun initFragments(fragmentPacks: Array<FragmentPack<out Fragment>>) {
        if (fragmentPacks.isNotEmpty() && fragmentManager.findByFragmentClass(fragmentPacks[0].clazz) == null) {
            Timber.d("initFragments")
            fragmentManager.commit {
                val newFragments = mutableListOf<Fragment>()
                fragmentPacks.forEachIndexed { index, fragmentPack ->
                    val newFragment = fragmentPack.producer.invoke()
                    if (index == initShowIndex) {
                        addWithTag(contentId, newFragment)
                    }
                    newFragments.add(newFragment)
                }

                fragments = newFragments.toTypedArray()
                currentShowFragmentIndex = initShowIndex
            }
        } else {
            Timber.d("reloadFragments")
            fragments = fragmentManager.fragments.toTypedArray()
            showHideFragments(initShowIndex)
        }
    }

    override fun showHideFragments(showIndex: Int) {
        if (showIndex != currentShowFragmentIndex && fragments?.isNotEmpty() == true) {
            if (currentShowFragmentIndex >= 0) {
                fragmentManager.commit {
                    val showFragment = fragments!![showIndex]
                    if (fragmentManager.findByFragmentClass(showFragment::class.java) == null) {
                        addWithTag(contentId, showFragment)
                    } else {
                        show(showFragment)
                    }
                    hide(fragments!![currentShowFragmentIndex])
                }
            } else {
                fragmentManager.commit {
                    fragments?.forEachIndexed { index, fragment ->
                        if (index == showIndex) show(fragment) else hide(fragment)
                    }
                }
            }

            currentShowFragmentIndex = showIndex
        }
    }

}