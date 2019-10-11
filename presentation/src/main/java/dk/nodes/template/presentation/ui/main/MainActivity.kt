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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation_Main.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.main_frame, MovieSearchFragment.newInstance(), "").commit()


    }

    fun replaceFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame,fragment).commit()
    }


    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        Timber.e(item.itemId.toString())

        when (item.itemId) {
            R.id.navigation_search -> supportFragmentManager.beginTransaction().add(R.id.main_frame, MovieSearchFragment.newInstance(), "").commit()


            R.id.navigation_savedphoto ->supportFragmentManager.beginTransaction().add(R.id.main_frame, ShowSavedMovieActivity.newInstance(), "").commit()
        }

        return false
    }


}




















