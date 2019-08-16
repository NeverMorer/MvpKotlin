package com.religion76.mvpkotlin.ui.post

import com.religion76.mvpkotlin.data.model.DataResult
import com.religion76.mvpkotlin.data.model.ListingPosts
import com.religion76.mvpkotlin.reddit.RedditApi
import com.religion76.mvpkotlin.ui.post.PostDataService
import retrofit2.Call

/**
 * Created by SunChao on 2019-08-13.
 */
class HotPostDataService(private val api: RedditApi) : PostDataService() {
    override fun getApi(pageSize: Int, after: String?): Call<DataResult<ListingPosts>> {
        return api.getHotPosts(pageSize, after)
    }
}