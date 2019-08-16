package com.religion76.mvpkotlin.base

import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.religion76.mvpkotlin.utils.StringUtils

/**
 * Created by SunChao on 2019-08-09.
 */

fun FragmentTransaction.addWithTag(@IdRes containerLayout: Int, fragment: Fragment) {
    add(containerLayout, fragment, fragment::class.java.name)
}

fun FragmentManager.findByFragmentClass(clazz: Class<out Fragment>): Fragment? {
    return findFragmentByTag(clazz.name)
}

fun <T : Fragment> FragmentManager.safeAdd(containerLayoutId: Int, fragmentPack: FragmentPack<T>) {
    var fragment = findByFragmentClass(fragmentPack.clazz)
    if (fragment == null) {
        fragment = fragmentPack.producer.invoke()
    }

    if (!fragment.isAdded) {
        commit {
            addWithTag(containerLayoutId, fragment)
        }
    }
}

fun Fragment.showMessage(message: String) {
    context?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showMessage(@StringRes messageId: Int) {
    context?.let {
        Toast.makeText(it, StringUtils.getString(messageId), Toast.LENGTH_SHORT).show()
    }
}
