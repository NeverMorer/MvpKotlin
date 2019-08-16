package com.religion76.mvpkotlin.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.religion76.mvpkotlin.reddit.RedditApi
import kotlinx.coroutines.*

/**
 * Created by SunChao
 * on 2019-08-15.
 */
class SearchRedditViewModel(private val api: RedditApi) : ViewModel() {

    private val hints = MutableLiveData<List<String>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Default + Job())

    private var preHintJob: Job? = null

    fun edit(query: String) {
        if (preHintJob?.isActive == true) {
            preHintJob!!.cancel()
        }

        preHintJob = coroutineScope.launch {
            withContext(Dispatchers.IO) {
                delay(500)
                if (isActive) {
                    api.searchRedditName(query).runCatching {
                        if (isActive){
                            hints.postValue(names)
                        }
                    }
                }
            }
        }
    }

    fun getHints(): LiveData<List<String>> = hints

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }
}