package dk.nodes.template.presentation.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private var shownMenu: Int = 0
    lateinit var movieSearchFragment :Fragment
    lateinit var showSavedMovieFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation_Main.setOnNavigationItemSelectedListener(this)


        showSavedMovieFragment =  ShowSavedMovieFragment.newInstance()
        movieSearchFragment = MovieSearchFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .add(R.id.main_frame, movieSearchFragment)
                .add(R.id.main_frame, showSavedMovieFragment)
                .hide(showSavedMovieFragment)
                .show(movieSearchFragment).commit()







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
            R.id.navigation_search -> {
                supportFragmentManager
                        .beginTransaction()
                        .show(movieSearchFragment)
                        .hide(showSavedMovieFragment)
                        .commit()

            }

            R.id.navigation_savedphoto -> {
                supportFragmentManager
                        .beginTransaction()
                        .hide(movieSearchFragment)
                        .show(showSavedMovieFragment)
                        .commit()

                Timber.e(item.itemId.toString())

            }

        }

        shownMenu = item.itemId


        return false
    }

}




















