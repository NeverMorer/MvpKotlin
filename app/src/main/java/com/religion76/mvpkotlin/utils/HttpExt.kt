package com.religion76.mvpkotlin.utils

import retrofit2.Response
import java.lang.Exception

/**
 * Created by SunChao on 2019-08-14.
 */

fun <T> retrofit2.Call<T>.executeAndGetResult(): HttpResponseResult<T> {
    var response: Response<T>? = null
    var exception: Exception? = null
    try {
        response = execute()
    } catch (e: Exception) {
        exception = e
    }

    return HttpResponseResult(response, exception)
}

fun <T> HttpResponseResult<T>.getResult(onResult: (T) -> Unit, onError: (Exception) -> Unit) {
    val responseBody = response?.body()
    if (response?.isSuccessful == true && responseBody != null) {
        onResult.invoke(responseBody)
    } else if (exception != null) {
        onError.invoke(exception)
    }
}


data class HttpResponseResult<T>(val response: Response<T>?, val exception: Exception?)
