package com.religion76.mvpkotlin.ui.search

import com.religion76.mvpkotlin.reddit.RedditApi
import com.religion76.mvpkotlin.ui.post.PostList
import org.koin.android.ext.android.inject

/**
 * Created by SunChao on 2019-08-16.
 */
class SearchRedditResult : PostList() {

    private val api: RedditApi by inject()


    override fun onLazyInit() {
        super.onLazyInit()

        val query = arguments?.getString("query")
        if (query.isNullOrEmpty()) {
            fragmentManager?.popBackStack()
            return
        }

        setDataService(SearchResultDataService(query, api))
    }
}