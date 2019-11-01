package dk.nodes.template.presentation.ui.main

import android.content.Context
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


class ShowSavedMoviesFragment : BaseFragment() {

    private var maincontext: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_saved_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createDiscoverAdapter(view)


    }

    fun onButtonPressed(uri: Uri) {
    }


    fun createDiscoverAdapter(view: View) {
        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        val framentArrayList = ArrayList<Fragment>()
        framentArrayList.add(MovieListFragment.newInstance(MovieViewType.SavedMovie))
        val tab_Titels = arrayOf(R.string.tab_text_8)
        val supportFragmentManager = childFragmentManager
        viewPager.adapter = SectionsPagerAdapter(maincontext!!, supportFragmentManager!!, tab_Titels, framentArrayList)
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
                ShowSavedMoviesFragment().apply {

                }
    }
}


