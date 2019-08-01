package com.religion76.commonlib.paging

/**
 * Created by SunChao on 2019-08-01.
 */
data class ListResult<T>(val data: List<T>, val hasMore: Boolean)