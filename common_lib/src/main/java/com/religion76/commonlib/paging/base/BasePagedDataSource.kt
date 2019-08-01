package com.religion76.commonlib.paging.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.religion76.commonlib.paging.ServiceLocator


/**
 * Created by sunwei on 2018/9/28.
 * Project: tarteeb
 */
abstract class BasePagedDataSource<Key, Value> : PageKeyedDataSource<Key, Value>() {

    val errorMessage = MutableLiveData<String>()
    val loadingState = MutableLiveData<NetworkState>()
    val initLoadingState = MutableLiveData<NetworkState>()

    protected var retry: (() -> Unit)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            ServiceLocator.networkIo.execute {
                it.invoke()
            }
        }
    }
}