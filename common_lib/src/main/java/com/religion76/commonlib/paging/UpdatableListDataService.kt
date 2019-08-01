package com.religion76.commonlib.paging

import androidx.annotation.WorkerThread


abstract class UpdatableListDataService<T>(override val pageSize: Int = 20) : SimplePagedListDataService<T>(pageSize) {

    @WorkerThread
    fun addItem(data: T, position: Int): Boolean {
        synchronized(this) {
            var startSize = 0
            for (i in 1 .. pages.size()) {
                pages.get(i)?.let { page ->
                    val endSize = startSize + page.size
                    if (position <= endSize) {
                        val offset = position - startSize
                        val newPage = mutableListOf<T>()
                        newPage.addAll(page)
                        newPage.add(offset, data)
                        updateDataCache(i, newPage)
                        return true
                    }
                    startSize += page.size
                }
            }

            return false
        }
    }

    @WorkerThread
    fun updateItem(data: T, block: (item: T) -> Boolean): Boolean {
        synchronized(this) {
            if (isPagesEmpty()) return false

            for (i in 1 .. pages.size()) {
                pages.get(i)?.let { page ->
                    page.forEachIndexed { index, oldItem ->
                        if (block.invoke(oldItem)) {
                            val newPage = mutableListOf<T>()
                            newPage.addAll(page)
                            newPage[index] = data
                            updateDataCache(i, newPage)
                            return true
                        }
                    }
                }
            }

            return false
        }
    }

    @WorkerThread
    fun deleteItem(block: (T) -> Boolean): Boolean {
        synchronized(this) {

            if (isPagesEmpty()) return false

            var result = false

            for (i in 1 .. pages.size()) {
                pages.get(i)?.let { page ->
                    if (page.find(block) != null) {
                        val newPage = mutableListOf<T>()
                        newPage.addAll(page)
                        newPage.removeAll(block)
                        result = true
                        updateDataCache(i, newPage)
                    }
                }
            }

            return result
        }
    }

}