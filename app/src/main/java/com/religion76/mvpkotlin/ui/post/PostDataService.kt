package com.religion76.mvpkotlin.ui.post

import com.religion76.commonlib.paging.ListResult
import com.religion76.commonlib.paging.SimplePagedListDataService
import com.religion76.mvpkotlin.data.model.DataResult
import com.religion76.mvpkotlin.data.model.ListingPosts
import com.religion76.mvpkotlin.data.model.RedditPostResult
import com.religion76.mvpkotlin.reddit.RedditApi
import com.religion76.mvpkotlin.utils.executeAndGetResult
import com.religion76.mvpkotlin.utils.getResult
import retrofit2.Call

/**
 * Created by SunChao on 2019-08-13.
 */
abstract class PostDataService : SimplePagedListDataService<RedditPostResult>() {

    override fun onLoadInitialData(
        onSuccess: (data: ListResult<out RedditPostResult>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        getApi(pageSize).executeAndGetResult().getResult(
            onResult = {
                onSuccess.invoke(ListResult(it.data.children, it.data.after))
            },
            onError = {
                onError.invoke(it.message ?: "")
            })
    }

    override fun onLoadAfterData(
        key: String,
        pageSize: Int,
        onSuccess: (data: ListResult<out RedditPostResult>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        getApi(pageSize, after = key).executeAndGetResult().getResult(
            onResult = {
                onSuccess.invoke(ListResult(it.data.children, it.data.after))
            },
            onError = {
                onError.invoke(it.message ?: "")
            })
    }

    abstract fun getApi(pageSize: Int, after: String? = null): Call<DataResult<ListingPosts>>


    override fun updateItem(item: RedditPostResult) {
    }

    override fun deleteItem(item: RedditPostResult) {
    }

    override fun addTopItem(item: RedditPostResult) {
    }

    override fun clear() {
    }
}