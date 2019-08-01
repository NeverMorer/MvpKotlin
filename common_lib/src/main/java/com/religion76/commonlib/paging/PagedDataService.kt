package com.religion76.commonlib.paging

import android.util.SparseArray
import androidx.annotation.CallSuper
import androidx.annotation.WorkerThread
import androidx.core.util.forEach
import androidx.core.util.isEmpty
import com.religion76.commonlib.paging.base.DataService

/**
 * Created by SunChao on 2019-08-01.
 */
abstract class PagedDataService<T> : DataService<T> {

    protected val pages = SparseArray<List<T>>()

    protected fun getCachedData(endPage: Int): List<T> {
        val cachedData = mutableListOf<T>()
        for (i in 1..endPage) {
            pages.get(i)?.let { cachedData.addAll(it) }
        }
        return cachedData
    }

    @WorkerThread
    fun updateDataCache(page: Int, data: List<T>) {
        pages.put(page, data)
    }

    fun isPagesEmpty(): Boolean {
        if (pages.isEmpty()) {
            return true
        }

        pages.forEach { key, page ->
            if (page.isNotEmpty()) {
                return false
            }
        }

        return true
    }

    @CallSuper
    override fun reset() {
        pages.clear()
    }
}