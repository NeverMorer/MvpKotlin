package com.religion76.mvpkotlin.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by SunChao on 2019-08-15.
 */
data class Names(@SerializedName("names") val names: List<String>)