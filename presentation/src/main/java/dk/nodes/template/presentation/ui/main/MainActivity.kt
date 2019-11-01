package dk.nodes.template.presentation.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private var shownMenu: Int = 0
    lateinit var discoverMoviesFragment: Fragment
    lateinit var showSavedMovieFragment: Fragment
    lateinit var searchMoviesFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation_Main.setOnNavigationItemSelectedListener(this)


        discoverMoviesFragment =  DiscoverMoviesFragment.newInstance()
        showSavedMovieFragment = ShowSavedMoviesFragment.newInstance()
        searchMoviesFragment = SearchMoviesFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .add(R.id.main_frame, discoverMoviesFragment,"1")
                .add(R.id.main_frame, showSavedMovieFragment,"2")
                .add(R.id.main_frame,searchMoviesFragment,"3")
                .hide(discoverMoviesFragment)
                .hide(showSavedMovieFragment)
                .show(searchMoviesFragment)
                .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        if(shownMenu == item.itemId) return false

        when (item.itemId) {
            R.id.navigation_Discover_Movies -> {
                supportFragmentManager
                        .beginTransaction()
                        .show(discoverMoviesFragment)
                        .hide(showSavedMovieFragment)
                        .hide(searchMoviesFragment)
                        .commit()

            }

            R.id.navigation_savedmovies -> {
                supportFragmentManager
                        .beginTransaction()
                        .hide(discoverMoviesFragment)
                        .show(showSavedMovieFragment)
                        .hide(searchMoviesFragment)
                        .commit()
            }
            R.id.navigation_Home -> {
                supportFragmentManager
                        .beginTransaction()
                        .hide(discoverMoviesFragment)
                        .hide(showSavedMovieFragment)
                        .show(searchMoviesFragment)
                        .commit()

            }


        }
        Timber.e(item.itemId.toString())



        shownMenu = item.itemId


        return false
    }

}






















