package com.religion76.mvpkotlin.data

import java.lang.Exception

/**
 * Created by SunChao on 2019-08-19.
 */
class Result<T> private constructor(
    val value: T?,
    val exception: Exception?,
    val isSuccessful: Boolean = false,
    val isErroneous: Boolean = false,
    val isLoading: Boolean = false
) {

    companion object {
        fun <T> success(value: T?): Result<T> = Result(value, null, isSuccessful = true)
        fun <T> error(value: T?, exception: Exception): Result<T> = Result(value, exception, isErroneous = true)
        fun <T> loading(value: T?): Result<T> = Result(value, null, isLoading = true)
    }

    override fun toString(): String {
        return "value: $value, isSuccessful: $isSuccessful, isErroneous: $isErroneous, isLoading: $isLoading"
    }

}