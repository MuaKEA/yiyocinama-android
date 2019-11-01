package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.experimental.ui.main.MovieViewType
import dk.nodes.template.presentation.ui.experimental.ui.main.SectionsPagerAdapter

private const val ARG_PARAM1 = "param1"



class DiscoverMoviesFragment : BaseFragment() {

    private var maincontext: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discover_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createDiscoverAdapter(view)


    }

    fun onButtonPressed(uri: Uri) {
    }


    fun createDiscoverAdapter(view : View){
        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        val framentArrayList = ArrayList<Fragment>()


        framentArrayList.add(MovieListFragment.newInstance(MovieViewType.Recommended))
        framentArrayList.add(MovieListFragment.newInstance(MovieViewType.TopRateMovie))
        framentArrayList.add(MovieListFragment.newInstance(MovieViewType.PopularMovie))
        framentArrayList.add(MovieListFragment.newInstance(MovieViewType.NowPlaying))
        val tab_Titels = arrayOf( R.string.tab_text_4, R.string.tab_text_5, R.string.tab_text_6, R.string.tab_text_7)
        val supportFragmentManager = childFragmentManager
        viewPager.offscreenPageLimit = 3;

        viewPager.adapter = SectionsPagerAdapter(maincontext!!, supportFragmentManager!!,tab_Titels,framentArrayList)
        tabs.setupWithViewPager(viewPager)


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
                DiscoverMoviesFragment().apply {

                }
    }




}
