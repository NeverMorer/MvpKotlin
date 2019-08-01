package com.religion76.commonlib.paging.base

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.religion76.commonlib.paging.ServiceLocator

/**
 * Created by sunwei on 2018/9/28.
 * Project: tarteeb
 */
abstract class BaseRepository<Key, Value>(val pageSize: Int) {

    var onZeroItemsLoaded: ((isZeroItems: Boolean) -> Unit)? = null

    fun getListing(): Listing<Value> {

        val sourceFactory = getSourceFactory()

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(pageSize)
                .setPrefetchDistance(2)
                .build()


        val livePagedList = LivePagedListBuilder(sourceFactory, config)
                .setFetchExecutor(ServiceLocator.networkIo)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<Value>() {
                    override fun onZeroItemsLoaded() {
                        super.onZeroItemsLoaded()
                        onZeroItemsLoaded?.invoke(true)
                    }

                    override fun onItemAtFrontLoaded(itemAtFront: Value) {
                        super.onItemAtFrontLoaded(itemAtFront)
                        onZeroItemsLoaded?.invoke(false)
                    }
                })
                .build()

        return Listing(
            pagedList = livePagedList,
            loadingState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.loadingState
            },
            errorMessage = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.errorMessage
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            initLoadingState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.initLoadingState
            }
        )

    }

    abstract fun getSourceFactory(): BaseDataSourceFactory<Key, Value>
}