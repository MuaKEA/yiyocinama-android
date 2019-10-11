package dk.nodes.template.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.hockeyapp.android.UpdateManager
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieActivity
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

        when (item.itemId) {

            R.id.navigation_search -> startActivity(Intent(this, MainActivity::class.java))

            R.id.navigation_savedphoto -> startActivity(Intent(this,ShowSavedMovieActivity::class.java))
        }

        return false
    }


}




















