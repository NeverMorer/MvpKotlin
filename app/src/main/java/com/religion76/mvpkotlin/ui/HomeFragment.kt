package com.religion76.mvpkotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.religion76.mvpkotlin.R
import com.religion76.mvpkotlin.base.FragmentPack
import com.religion76.mvpkotlin.base.LazyFragment
import com.religion76.mvpkotlin.base.safeAdd
import com.religion76.mvpkotlin.ui.post.HotPostDataService
import com.religion76.mvpkotlin.ui.post.PostList
import com.religion76.mvpkotlin.ui.register.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Created by SunChao on 2019-08-09.
 */
class HomeFragment : LazyFragment() {

    private val userViewModel: UserViewModel by viewModel()
    private val hotPostDataService: HotPostDataService by inject()

    private var isContentInit = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onLazyInit() {
        userViewModel.getUser().observe(this, Observer { user ->
            if (user == null) {
                findNavController().run {
                    if (currentDestination?.id == R.id.homeFragment)
                        navigate(R.id.action_homeFragment_to_register)
                }
            } else {
                initContent()
            }
        })
    }

    private fun initContent() {
        if (isContentInit) return

        layoutSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        ivReddit.setOnClickListener {
            userViewModel.deleteUser()
        }

        childFragmentManager.safeAdd(R.id.containerPosts, FragmentPack(PostList::class.java) {
            PostList(hotPostDataService)
        })
    }
}