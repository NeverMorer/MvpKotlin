package com.religion76.commonlib.paging.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.religion76.commonlib.paging.ServiceLocator
import com.religion76.commonlib.paging.SimpleRepository

abstract class BaseDataServiceViewModel<T> : ViewModel() {
    protected val service = MutableLiveData<DataService<T>>()
    private val repo = map(service) {
        SimpleRepository(it).apply {
            onZeroItemsLoaded = { isZeroItems.postValue(it) }
        }
    }

    // repo 的 getListing() 每次调用都会创建一个新的listing
    private val listing = map(repo) { it.getListing() }

    val isZeroItems = MutableLiveData<Boolean>()
    val dataList = switchMap(listing) { it.pagedList }
    val loadingState = switchMap(listing) { it.loadingState }
    val initLoadingState = switchMap(listing) { it.initLoadingState }
    val errorMessage = switchMap(listing) { it.errorMessage }

    fun getService(): DataService<T>? = service.value

    fun setService(dataService: DataService<T>) {
        service.postValue(dataService)
    }

    fun reset() {
        service.value?.reset()
    }

    fun retry() {
        listing.value?.retry?.invoke()
    }

    fun refresh() {
        isZeroItems.postValue(false)
        listing.value?.refresh?.invoke()
    }

    fun reloadWhenEmpty() {
        isZeroItems.postValue(false)
        reset()
        refresh()
    }
}