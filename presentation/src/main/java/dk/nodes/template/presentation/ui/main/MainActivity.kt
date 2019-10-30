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
import dk.nodes.template.presentation.ui.experimental.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_search.*
import timber.log.Timber


class MainActivity : BaseActivity(), SearchView.OnQueryTextListener {
    private var adapter: SectionsPagerAdapter? = null
    private var oldQuaryLength = 0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input_search.setOnQueryTextListener(this)
        adapter = SectionsPagerAdapter(this, supportFragmentManager)

        createTabviewAdapter(input_search.query.toString())


        adapter?.notifyDataSetChanged()

    }
    fun createTabviewAdapter(movieName :String){
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adapter
        adapter?.addString(movieName)
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText?.length!! > oldQuaryLength.plus(2)) {
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
        input_search.clearFocus()
        adapter?.addString(query.toString())
        adapter?.notifyDataSetChanged()

        return true
    }
}




