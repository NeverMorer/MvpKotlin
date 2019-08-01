package com.religion76.commonlib.paging

import com.religion76.commonlib.paging.base.DataService


abstract class SimpleListDataService<T>(override val pageSize: Int = 20) : DataService<T> {

    protected var cachedList: List<T>? = null

    @Volatile
    private var currentPage = 0

    abstract fun onLoadInitialData(onSuccess: (data: ListResult<out T>) -> Unit, onError: (errorMessage: String) -> Unit)

    abstract fun onLoadAfterData(page: Int, pageSize: Int, onSuccess: (data: ListResult<out T>) -> Unit, onError: (errorMessage: String) -> Unit)

    override fun loadInitial(onSuccess: (data: List<T>, nextKey: String?) -> Unit, onError: (errorMessage: String) -> Unit) {
        if (cachedList != null) {
            onSuccess.invoke(cachedList!!, (currentPage + 1).toString())
            return
        }

        onLoadInitialData({ result ->
            val data = mutableListOf<T>()
            data.addAll(result.data)
            val hasMore = if (result.hasMore != null) {
                result.hasMore!!
            } else {
                data.size >= pageSize - DataConstants.HAS_MORE_OFFSET
            }
            val nextKey = if (hasMore) "2" else null

            val list = mutableListOf<T>()
            list.addAll(data)
            cachedList = list
            currentPage = 1
            onSuccess.invoke(data, nextKey)

        }, {
            onError.invoke(it)
        })

    }

    override fun loadAfter(key: String, onSuccess: (data: List<T>, nextKey: String?) -> Unit, onError: (errorMessage: String) -> Unit) {

        val page = key.toIntOrNull() ?: return

        onLoadAfterData(page, pageSize, {
            val data = it.data
            val hasMore = if (it.hasMore != null) {
                it.hasMore!!
            } else {
                data.size >= pageSize - DataConstants.HAS_MORE_OFFSET
            }
            val nextKey = if (hasMore) (page + 1) else null

            val list = mutableListOf<T>()
            cachedList?.run { list.addAll(this) }
            list.addAll(data)
            cachedList = list
            currentPage = page
            onSuccess.invoke(data, nextKey.toString())
        }, {
            onError.invoke(it)
        })

    }

    override fun reset() {
        cachedList = null
        currentPage = 0
    }


}