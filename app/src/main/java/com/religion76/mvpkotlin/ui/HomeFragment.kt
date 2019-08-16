package com.religion76.mvpkotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.religion76.mvpkotlin.R
import com.religion76.mvpkotlin.base.FragmentPack
import com.religion76.mvpkotlin.base.LazyFragment
import com.religion76.mvpkotlin.base.safeAdd
import com.religion76.mvpkotlin.ui.post.HotPostDataService
import com.religion76.mvpkotlin.ui.post.PostList
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

/**
 * Created by SunChao on 2019-08-09.
 */
class HomeFragment : LazyFragment() {

    private val hotPostDataService: HotPostDataService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onLazyInit() {
        layoutSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        childFragmentManager.safeAdd(R.id.containerPosts, FragmentPack(PostList::class.java) {
            PostList(hotPostDataService)
        })
    }

}