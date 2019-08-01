package com.religion76.navigationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_navigation.*
import timber.log.Timber

class NavigationActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private var showTopMenu = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        navController = findNavController(R.id.fragmentRoot)
        navController?.addOnDestinationChangedListener { controller, destination, arguments ->
            showTopMenu = destination.id != R.id.editFragment
            invalidateOptionsMenu()
        }

//        navController?.let { bottomNavView.setupWithNavController(it) }
        bottomNavView.setOnNavigationItemSelectedListener { menuItem ->
            try {
                navController?.navigate(menuItem.itemId)
                true
            } catch (t: Throwable) {
                t.printStackTrace()
                false
            }
        }

        Timber.d("threads: ${Thread.activeCount()}")
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.menu_edit)?.isVisible = showTopMenu
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_edit) {
            navController?.navigate(R.id.action_homeFragment_to_editFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
