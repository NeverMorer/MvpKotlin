package com.religion76.mvpkotlin

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.religion76.mvpkotlin.utils.setupWithNavController
import com.religion76.mvpkotlin.utils.visibleBy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            initNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        /**
         * temporary solution for multiple backstacks
         * track -> https://issuetracker.google.com/issues/80029773#comment25
         */
        val navControllerLiveData = bottomNavi.setupWithNavController(
            listOf(R.navigation.navi_home, R.navigation.navi_discovery),
            supportFragmentManager,
            R.id.containerFragments,
            intent
        )

        navControllerLiveData.observe(this, Observer {
            if (it.currentDestination?.id == R.id.homeFragment) {
                it.addOnDestinationChangedListener { controller, destination, arguments ->
                    bottomNavi.visibleBy { destination.id == R.id.homeFragment }
                }
            }
        })
    }
}
