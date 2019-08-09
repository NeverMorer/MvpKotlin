package com.religion76.mvpkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.religion76.mvpkotlin.base.FragmentPack
import com.religion76.mvpkotlin.base.MultiFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MultiFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavi.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    showHideFragments(0)
                    true
                }
                R.id.menu_discovery -> {
                    showHideFragments(1)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override val fragmentPacks: Array<FragmentPack<out Fragment>> =
        arrayOf(
            FragmentPack(HomeFragment::class.java) { HomeFragment() },
            FragmentPack(DiscoveryFragment::class.java) { DiscoveryFragment() })

    override val initShowIndex: Int = 0

    override fun getFragmentContainerId(): Int = R.id.containerFragments

}
