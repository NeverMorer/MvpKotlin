package com.religion76.mvpkotlin.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by SunChao on 2019-08-13.
 */
data class RedditPost(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("subreddit")
    val subreddit: String,
    @SerializedName("subreddit_name_prefixed")
    val subredditPrefixed: String,
    @SerializedName("num_comments")
    val num_comments: Int,
    @SerializedName("created_utc")
    val created: Long,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("preview")
    val preview: PostPreview?
)