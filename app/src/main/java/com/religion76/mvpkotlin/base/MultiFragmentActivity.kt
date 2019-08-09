package com.religion76.mvpkotlin.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import timber.log.Timber

/**
 * Created by SunChao
 * on 2019-08-09.
 */
abstract class MultiFragmentActivity : FragmentActivity() {

    private var currentShowFragmentIndex = -1

    private var fragments: Array<Fragment>? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initFragments()
    }

    private fun initFragments() {
        if (isNeedReInit()) {
            Timber.d("initFragments")
            supportFragmentManager.commit {
                val newFragments = mutableListOf<Fragment>()
                fragmentPacks.forEachIndexed { index, fragmentPack ->
                    val newFragment = fragmentPack.producer.invoke()
                    addWithTag(getFragmentContainerId(), newFragment)
                    newFragments.add(newFragment)
                    if (index != initShowIndex) {
                        hide(newFragment)
                    }
                }

                fragments = newFragments.toTypedArray()
                currentShowFragmentIndex = initShowIndex
            }
        } else {
            Timber.d("reloadFragments")
            fragments = supportFragmentManager.fragments.toTypedArray()
            showHideFragments(initShowIndex)
        }
    }

    private fun isNeedReInit(): Boolean {
        return fragmentPacks.isNotEmpty() && supportFragmentManager.findByFragmentClass(fragmentPacks[0].clazz) == null
    }

    protected fun showHideFragments(showIndex: Int) {
        if (showIndex != currentShowFragmentIndex && fragments?.isNotEmpty() == true) {
            if (currentShowFragmentIndex >= 0) {
                supportFragmentManager.commit {
                    show(fragments!![showIndex])
                    hide(fragments!![currentShowFragmentIndex])
                }
            } else {
                supportFragmentManager.commit {
                    fragments?.forEachIndexed { index, fragment ->
                        if (index == showIndex) show(fragment) else hide(fragment)
                    }
                }
            }

            currentShowFragmentIndex = showIndex
        }
    }

    abstract val fragmentPacks: Array<FragmentPack<out Fragment>>

    abstract val initShowIndex: Int

    @IdRes
    abstract fun getFragmentContainerId(): Int
}