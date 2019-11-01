package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.experimental.ui.main.MovieViewType
import dk.nodes.template.presentation.ui.experimental.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.fragment_search_movies.*


class SearchMoviesFragment : BaseFragment(), SearchView.OnQueryTextListener {
    private var maincontext: Context? = null
    private var oldQuaryLength = 0
    private val framentArrayList = ArrayList<Fragment>()
    private var adapter: PagerAdapter? = null

    var movieSearchFragment = MovieListFragment.newInstance(MovieViewType.Movie)
    var movieActionFragment = MovieListFragment.newInstance(MovieViewType.ActionMovie)
    var movieDramaFragment = MovieListFragment.newInstance(MovieViewType.DramaMovie)
    var movieHorrorFragment = MovieListFragment.newInstance(MovieViewType.HorrorMovie)
    var movieComedyFragment = MovieListFragment.newInstance(MovieViewType.ComedyMovie)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createDiscoverAdapter(view)


    }

    fun onButtonPressed(uri: Uri) {
    }


    fun createDiscoverAdapter(view : View){
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        val viewPager: ViewPager = view.findViewById(R.id.view_pager)

        framentArrayList.add(movieSearchFragment)
        framentArrayList.add(movieActionFragment)
        framentArrayList.add(movieDramaFragment)
        framentArrayList.add(movieComedyFragment)
        framentArrayList.add(movieHorrorFragment)

        val tab_Titels = arrayOf( R.string.tab_text_0, R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3,R.string.tab_text_9)
        val supportFragmentManager = childFragmentManager

        viewPager.adapter = SectionsPagerAdapter(maincontext!!, supportFragmentManager,tab_Titels,framentArrayList)
        adapter = viewPager.adapter
        tabs.setupWithViewPager(viewPager)

        input_search.setOnQueryTextListener(this)

    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        maincontext = context

    }

    override fun onDetach() {
        super.onDetach()
    }



    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                SearchMoviesFragment().apply {

                }
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText?.length!! > oldQuaryLength.plus(2)) {
            input_search.clearFocus()
            movieSearchFragment.updateAdapter(newText.toString())
            adapter?.notifyDataSetChanged()
            return true
        }
        oldQuaryLength = newText.length
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        movieSearchFragment.updateAdapter(query.toString())
        adapter?.notifyDataSetChanged()
        input_search.clearFocus()
        oldQuaryLength = query?.length!!

        return true
    }



}
