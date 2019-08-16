package com.religion76.mvpkotlin.reddit

import com.religion76.mvpkotlin.data.model.DataResult
import com.religion76.mvpkotlin.data.model.ListingPosts
import com.religion76.mvpkotlin.data.model.Names
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by SunChao on 2019-08-13.
 */

interface RedditApi {

    @GET("/hot.json")
    fun getHotPosts(@Query("limit") limit: Int, @Query("before") before: String? = null, @Query("after") after: String? = null): Call<DataResult<ListingPosts>>

    @GET("api/search_reddit_names.json")
    suspend fun searchRedditName(@Query("query") query: String): Names

    @GET("/r/{subreddit}/hot.json")
    fun searchPostBySubreddit(@Path("subreddit") subreddit:String, @Query("limit") limit: Int, @Query("before") before: String? = null, @Query("after") after: String? = null): Call<DataResult<ListingPosts>>

}