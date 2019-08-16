package com.religion76.mvpkotlin.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.religion76.mvpkotlin.R
import com.religion76.mvpkotlin.base.LazyFragment
import com.religion76.mvpkotlin.utils.hideKeyBoard
import com.religion76.mvpkotlin.utils.showKeyBoard
import com.religion76.mvpkotlin.utils.simpleTextChangeListener
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by SunChao on 2019-08-15.
 */
class SearchReddit : LazyFragment() {

    private val searchViewModel: SearchRedditViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onLazyInit() {
        val adapter = SearchHinstAdapter()
        rvHints.adapter = adapter

        adapter.onHintClick = {
            goToResult(it)
        }

        searchViewModel.getHints().observe(this, Observer {
            adapter.hints = it
        })

        etSearch.simpleTextChangeListener { s, start, before, count ->
            s?.toString()?.let { searchViewModel.edit(it) }
        }

//        etSearch.post {
//            etSearch.showKeyBoard()
//        }

        etSearch.setOnEditorActionListener { v, actionId, event ->
            val entered = etSearch.text?.toString()
            if ((actionId == EditorInfo.IME_ACTION_SEARCH) && (entered?.isNotEmpty() == true)) {
                goToResult(entered)
                true
            } else {
                false
            }
        }

        etSearch.post {
            etSearch.requestFocus()
        }
    }

    private fun goToResult(query: String) {
        etSearch.hideKeyBoard()
        findNavController().navigate(R.id.action_searchReddit_to_searchRedditResult, Bundle().also {
            it.putString("query", query)
        })
    }
}