package com.religion76.commonlib.paging

import java.util.concurrent.atomic.AtomicInteger


abstract class SimplePagedListDataService<T>(override val pageSize: Int = 20) : PagedDataService<T>() {


    private val sessionId = AtomicInteger(0)
    private var currentPage = 0
    private var hasNextPage = true

    abstract fun onLoadInitialData(
        onSuccess: (data: ListResult<out T>) -> Unit,
        onError: (errorMessage: String) -> Unit
    )

    abstract fun onLoadAfterData(
        page: Int,
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
            onSuccess.invoke(getCachedData(currentPage), if (hasNextPage) (currentPage + 1).toString() else null)
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
            updateDataCache(1, data)

            currentPage = 1
            onSuccess.invoke(data, nextKey)

        }, {
            onError.invoke(it)
        })

    }

    override fun loadAfter(
        key: String,
        onSuccess: (data: List<T>, nextKey: String?) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {

        val page = key.toIntOrNull() ?: return

        val currentSessionId = getCurrentSessionId()

        onLoadAfterData(page, pageSize, {

            if (!checkSessionValidated(currentSessionId)) return@onLoadAfterData

            val data = it.data
            val hasMore = if (it.hasMore != null) {
                it.hasMore!!
            } else {
                data.size >= pageSize - DataConstants.HAS_MORE_OFFSET
            }

            currentPage = page
            hasNextPage = hasMore

            val nextKey = if (hasMore) (page + 1) else null

            updateDataCache(page, data)
            onSuccess.invoke(data, nextKey.toString())
        }, {
            onError.invoke(it)
        })
    }

    fun getCachedData(): List<T> {
        return getCachedData(currentPage)
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
        currentPage = 0
        hasNextPage = true
    }

}