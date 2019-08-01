package com.religion76.commonlib.paging

import com.religion76.commonlib.paging.base.BaseDataServiceViewModel

/**
 * Created by SunChao on 2019-08-01.
 */
abstract class UpdatablePagedViewModel<T> : BaseDataServiceViewModel<T>() {

    fun updateItemAndRefresh(item: T) {
        executeAsync {
            getService()?.updateItem(item)
            refresh()
        }
    }

    fun deleteItemAndRefresh(item: T) {
        executeAsync {
            getService()?.deleteItem(item)
            refresh()
        }
    }

    fun addTopItemAndRefresh(item: T) {
        executeAsync {
            getService()?.addTopItem(item)
            refresh()
        }
    }

    protected fun executeAsync(block: () -> Unit) {
        ServiceLocator.workers.run {
            block.invoke()
        }
    }
}