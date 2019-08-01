package com.religion76.commonlib.paging

import com.religion76.commonlib.paging.base.BasePagedDataSource
import com.religion76.commonlib.paging.base.DataService
import com.religion76.commonlib.paging.base.NetworkState
import timber.log.Timber


class SimpleDataSource<T>(private val service: DataService<T>) : BasePagedDataSource<String, T>() {


    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, T>) {
        Timber.d("loadInitial:")

        initLoadingState.postValue(NetworkState.Loading)
        errorMessage.postValue(null)

        service.loadInitial({ data, nextKey ->
            retry = null
            initLoadingState.postValue(NetworkState.Loaded)
            loadingState.postValue(NetworkState.Loaded)
            errorMessage.postValue(null)
            callback.onResult(data, null, nextKey)
        }, {
            retry = { invalidate() }
            initLoadingState.postValue(NetworkState.Error)
            loadingState.postValue(NetworkState.Error)
            errorMessage.postValue(it)
        })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, T>) {
        Timber.d("loadAfter: key = ${params.key}")

        initLoadingState.postValue(NetworkState.Loaded)
        loadingState.postValue(NetworkState.Loaded)
        errorMessage.postValue(null)

        val key = params.key.toIntOrNull() ?: return

        loadingState.postValue(NetworkState.Loading)
        errorMessage.postValue(null)


        service.loadAfter(key.toString(), { data, nextKey ->
            retry = null
            loadingState.postValue(NetworkState.Loaded)
            errorMessage.postValue(null)
            if (!data.isEmpty()) {
                callback.onResult(data, nextKey)
            }
        }, {
            retry = { loadAfter(params, callback) }
            loadingState.postValue(NetworkState.Error)
            errorMessage.postValue(it)
        })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, T>) {
    }

}