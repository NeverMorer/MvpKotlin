package com.religion76.mvpkotlin.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by SunChao on 2019-08-14.
 */

data class PostImageItem(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)


data class PostImageSource(
    @SerializedName("id") val id: String,
    @SerializedName("source") val source: PostImageItem,
    @SerializedName("resolutions") val resolutions: List<PostImageItem>
)

data class PostPreview(
    @SerializedName("images") val images: List<PostImageSource>,
    @SerializedName("enabled") val enabled: Boolean
)

