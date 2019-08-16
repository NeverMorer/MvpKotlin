package com.religion76.commonlib.paging

import androidx.annotation.CallSuper
import androidx.annotation.WorkerThread
import com.religion76.commonlib.paging.base.DataService

/**
 * Created by SunChao on 2019-08-01.
 */
abstract class PagedDataService<T> : DataService<T> {

    companion object{
        const val DEFAULT_TOP_KEY = "first"
    }

    protected val pages = LinkedHashMap<String, List<T>>()

    protected fun getCachedData(endPageKey: String?): List<T> {
        val cachedData = mutableListOf<T>()

        pages.keys.forEach {
            pages.get(it)?.let { cachedData.addAll(it) }
        }

        return cachedData
    }

    @WorkerThread
    fun updateDataCache(key: String, data: List<T>) {
        pages[key] = data
    }

    fun isPagesEmpty(): Boolean {
        if (pages.isEmpty()) {
            return true
        }

        pages.keys.forEach {
            if (pages[it]?.isNotEmpty() == false) {
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