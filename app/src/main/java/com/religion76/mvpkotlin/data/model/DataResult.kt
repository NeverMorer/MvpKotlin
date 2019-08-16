package com.religion76.mvpkotlin.data.model

/**
 * Created by SunChao on 2019-08-13.
 */

data class DataResult<T>(val kind: String, val data: T)

data class ListingPosts(val before: String?, val after: String?, val children: List<RedditPostResult>)

data class RedditPostResult(val kind: String, val data: RedditPost)