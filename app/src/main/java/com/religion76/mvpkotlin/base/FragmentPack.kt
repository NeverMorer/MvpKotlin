package com.religion76.mvpkotlin.base

import androidx.fragment.app.Fragment

/**
 * Created by SunChao on 2019-08-09.
 */
class FragmentPack<T : Fragment>(val clazz: Class<T>, val producer: (() -> T))