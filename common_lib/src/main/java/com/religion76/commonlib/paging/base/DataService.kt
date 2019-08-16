package com.religion76.commonlib.paging.base

interface DataService<T> {

    fun loadInitial(onSuccess: (data: List<T>, nextKey: String?) -> Unit, onError: (errorMessage: String) -> Unit)

    fun loadAfter(key: String, onSuccess: (data: List<T>, nextKey: String?) -> Unit, onError: (errorMessage: String) -> Unit)

    fun updateItem(item: T)

    fun deleteItem(item: T)

    fun addTopItem(item: T)

    fun reset()

    fun clear()

    val pageSize: Int
}