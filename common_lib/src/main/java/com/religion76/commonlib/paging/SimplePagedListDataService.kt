package com.religion76.commonlib.paging

import java.util.concurrent.atomic.AtomicInteger


abstract class SimplePagedListDataService<T>(override val pageSize: Int = 20) : PagedDataService<T>() {


    private val sessionId = AtomicInteger(0)

    private var currentPageKey: String? = null
    private var nextPageKey: String? = null

    abstract fun onLoadInitialData(
        onSuccess: (data: ListResult<out T>) -> Unit,
        onError: (errorMessage: String) -> Unit
    )

    abstract fun onLoadAfterData(
        key: String,
        pageSize: Int,
        onSuccess: (data: ListResult<out T>) -> Unit,
        onError: (errorMessage: String) -> Unit
    )

    override fun loadInitial(
        onSuccess: (data: List<T>, nextKey: String?) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {

        updatePageSession()

        if (!isPagesEmpty()) {
            onSuccess.invoke(getCachedData(currentPageKey), nextPageKey)
            return
        }

        onLoadInitialData({ result ->
            val data = mutableListOf<T>()
            data.addAll(result.data)
            nextPageKey = result.nextKey
            updateDataCache(DEFAULT_TOP_KEY, data)

            currentPageKey = DEFAULT_TOP_KEY
            onSuccess.invoke(data, nextPageKey)

        }, {
            onError.invoke(it)
        })

    }

    override fun loadAfter(
        key: String,
        onSuccess: (data: List<T>, nextKey: String?) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {


        val currentSessionId = getCurrentSessionId()

        onLoadAfterData(key, pageSize, {

            if (!checkSessionValidated(currentSessionId)) return@onLoadAfterData

            val data = it.data

            currentPageKey = key
            nextPageKey = it.nextKey

            updateDataCache(key, data)
            onSuccess.invoke(data, nextPageKey)
        }, {
            onError.invoke(it)
        })
    }

    fun getCachedData(): List<T> {
        return getCachedData(currentPageKey)
    }

    private fun updatePageSession(): Int {
        return sessionId.incrementAndGet()
    }

    private fun checkSessionValidated(sessionId: Int): Boolean {
        return sessionId == this.sessionId.get()
    }

    private fun getCurrentSessionId(): Int = sessionId.get()


    override fun reset() {
        super.reset()
        currentPageKey = null
        nextPageKey = null
    }

}