package com.religion76.mvpkotlin.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Created by SunChao on 2019-08-09.
 */

fun FragmentTransaction.addWithTag(@IdRes containerLayout: Int, fragment: Fragment) {
    add(containerLayout, fragment, fragment::class.java.name)
}

fun FragmentManager.findByFragmentClass(clazz: Class<out Fragment>): Fragment? {
    return findFragmentByTag(clazz.name)
}