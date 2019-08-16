package com.religion76.mvpkotlin.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.religion76.commonlib.paging.base.DataService
import com.religion76.commonlib.paging.base.NetworkState
import com.religion76.mvpkotlin.R
import com.religion76.mvpkotlin.base.LazyFragment
import com.religion76.mvpkotlin.data.model.RedditPostResult
import kotlinx.android.synthetic.main.fragment_post_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Created by SunChao on 2019-08-16.
 */
open class PostList(private var dataService: DataService<RedditPostResult>? = null) : LazyFragment() {

    private val postViewModel: PostViewModel by viewModel()
    private val adapter: RedditPostAdapter = RedditPostAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onLazyInit() {

        val ctx = context ?: return

        rvPostList.setHasFixedSize(true)
        rvPostList.adapter = adapter

        ContextCompat.getDrawable(ctx, R.drawable.shape_divider_post_list)?.let {
            val divider = DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL)
            divider.setDrawable(it)
            rvPostList.addItemDecoration(divider)
        }

        srlPostList.setColorSchemeResources(R.color.colorAccent)
        srlPostList.setOnRefreshListener {
            postViewModel.refresh()
        }

        postViewModel.initLoadingState.observe(this, Observer {
            srlPostList.isRefreshing = it == NetworkState.Loading
        })

        postViewModel.dataList.observe(this, Observer {
            adapter.submitList(it)
        })

        if (postViewModel.getService() == null && dataService != null) {
            postViewModel.setService(dataService!!)
        }
    }

    fun setDataService(dataService: DataService<RedditPostResult>) {
        if (postViewModel.getService() != null || this.dataService == null) {
            postViewModel.setService(dataService)
        } else {
            this.dataService = dataService
        }
    }
}