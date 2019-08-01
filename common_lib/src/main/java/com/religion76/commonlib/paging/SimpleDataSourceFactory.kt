package com.religion76.commonlib.paging

import com.religion76.commonlib.paging.base.BaseDataSourceFactory
import com.religion76.commonlib.paging.base.BasePagedDataSource
import com.religion76.commonlib.paging.base.DataService

class SimpleDataSourceFactory<T>(private val service: DataService<T>) : BaseDataSourceFactory<String, T>() {

    override fun onCreateSource(): BasePagedDataSource<String, T> {
        return SimpleDataSource(service)
    }
}