package dk.nodes.template.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.experimental.ui.main.DiscoverMoviesAdapter
import dk.nodes.template.presentation.ui.experimental.ui.main.SaveMovieAdapter
import dk.nodes.template.presentation.ui.experimental.ui.main.SectionsPagerAdapter
import dk.nodes.template.presentation.ui.savedmovies.SavedMoviesAdapter
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_search.*
import timber.log.Timber


class MainActivity : BaseActivity(), SearchView.OnQueryTextListener,BottomNavigationView.OnNavigationItemSelectedListener {
    private var adapter: SectionsPagerAdapter? = null
    private var discoverMovieAdapter : DiscoverMoviesAdapter? = null
    private var saveMoviesAdapter: SaveMovieAdapter? = null

    private var oldQuaryLength = 0
    private var shownMenu: Int = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input_search.setOnQueryTextListener(this)
        adapter = SectionsPagerAdapter(this, supportFragmentManager)
        discoverMovieAdapter = DiscoverMoviesAdapter(this,supportFragmentManager)
        saveMoviesAdapter = SaveMovieAdapter(this, )
        createTabviewAdapter(input_search.query.toString())
        adapter?.notifyDataSetChanged()


        bottomNavigation_Main.setOnNavigationItemSelectedListener(this)



    }

    fun createSavedMovieAdapter(movieName :String){
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = saveMoviesAdapter
        adapter?.addString(movieName)
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
    fun createTabviewAdapter(movieName :String){
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adapter
        adapter?.addString(movieName)
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }


    fun createDiscoverAdapter(movieName :String){
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = discoverMovieAdapter
        adapter?.addString(movieName)
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()

    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText?.length!! > oldQuaryLength.plus(1)) {
           adapter?.addString(newText)
            input_search.clearFocus()
            adapter?.notifyDataSetChanged()

            return true
        }
        oldQuaryLength = newText.length
        adapter?.addString(newText)
        adapter?.notifyDataSetChanged()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter?.addString(query.toString())
        adapter?.notifyDataSetChanged()
        input_search.clearFocus()

        return true
    }




    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        if(shownMenu == item.itemId) return false




        when (item.itemId) {
            R.id.navigation_Home -> {
                createTabviewAdapter("")

            }

            R.id.navigation_Discover_Movies -> {

                createDiscoverAdapter(input_search.query.toString())

                Timber.e(item.itemId.toString())

            }

            R.id.navigation_savedmovies -> {
                createSavedMovieAdapter(input_search.query.toString())


                Timber.e(item.itemId.toString())

            }

        }

        shownMenu = item.itemId


        return false
    }

}




