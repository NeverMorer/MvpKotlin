package com.religion76.commonlib.paging.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

/**
 * Created by sunwei on 2018/9/28.
 * Project: tarteeb
 */

abstract class BaseDataSourceFactory<Key, Value> : DataSource.Factory<Key, Value>() {

    val sourceLiveData = MutableLiveData<BasePagedDataSource<Key, Value>>()

    override fun create(): DataSource<Key, Value> {
        val source = onCreateSource()
        sourceLiveData.postValue(source)
        return source

    }

    abstract fun onCreateSource(): BasePagedDataSource<Key, Value>


}