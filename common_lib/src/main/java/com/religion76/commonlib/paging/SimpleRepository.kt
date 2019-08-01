package com.religion76.commonlib.paging

import com.religion76.commonlib.paging.base.BaseDataSourceFactory
import com.religion76.commonlib.paging.base.BaseRepository
import com.religion76.commonlib.paging.base.DataService

class SimpleRepository<T>(private val service: DataService<T>) : BaseRepository<String, T>(service.pageSize) {

    override fun getSourceFactory(): BaseDataSourceFactory<String, T> {
        return SimpleDataSourceFactory(service)
    }
}
