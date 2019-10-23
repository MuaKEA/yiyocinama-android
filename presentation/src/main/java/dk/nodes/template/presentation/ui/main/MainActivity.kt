package dk.nodes.template.presentation.ui.main

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private var shownMenu: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation_Main.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.main_frame, MovieSearchFragment.newInstance(), "").commit()


    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, MovieSearchFragment.newInstance(), "movieSearch").commit()



    }

    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        if(shownMenu == item.itemId) return false


        when (item.itemId) {
            R.id.navigation_search -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frame, MovieSearchFragment.newInstance(), "movieSearch").commit()
            }

            R.id.navigation_savedphoto -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frame, ShowSavedMovieActivity.newInstance(), "ShowSavedMovieActivity").commit()


                Timber.e(item.itemId.toString())

            }

        }

        shownMenu = item.itemId


        return false
    }

    override fun onDestroy() {
        super.onDestroy()


    }
}




















