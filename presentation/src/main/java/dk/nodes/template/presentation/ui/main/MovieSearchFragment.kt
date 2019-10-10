package dk.nodes.template.presentation.ui.main

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Adapter
import android.widget.SearchView
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieActivity
import kotlinx.android.synthetic.main.fragment_movie_search.*
import net.hockeyapp.android.UpdateManager
import timber.log.Timber





class MovieSearchFragment : BaseFragment, SearchView.OnQueryTextListener, BottomNavigationView.OnNavigationItemSelectedListener {


    private val viewModel by viewModel<MainActivityViewModel>()
    private var adapter : MoviesAdapter?  = null
    private var listener: OnFragmentInteractionListener? = null

    constructor() : super()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_movie_search, container, false)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
         adapter = MoviesAdapter(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isDeviceOnlineCheck()
        viewModel.viewState.observeNonNull(this) { state ->
            handleMovies(state)
            handleErrors(state)
        }

        input_search.setOnQueryTextListener(this)

        bottomNavigation_View.setOnNavigationItemSelectedListener(this)
        adapter?.notifyDataSetChanged()

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }



//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                MovieSearchFragment().apply {

                    }
                }

    private fun handleMovies(viewState: MainActivityViewState) {
        viewState.movies?.let { movieList ->
         Timber.e(movieList.toString())
            error_view.visibility = View.INVISIBLE
            adapter?.addMovies(movieList)
            adapter?.notifyDataSetChanged()
        }
    }

    private fun handleErrors(viewState: MainActivityViewState) {
        viewState?.viewError?.let {
            if (it.consumed) return@let
            Timber.e("no internet")
            Glide.with(this).asGif().load(R.drawable.nointernetconnection).into(error_view)
            error_view.visibility = View.VISIBLE
            Snackbar.make(rv_moviesList, "No Internet OR No Result", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateRecyclerview() {
        // Creates a vertical Layout Manager
        rv_moviesList.layoutManager = GridLayoutManager(context, 3)
        // Access the RecyclerView Adapter and load the data into it
        rv_moviesList.adapter = adapter
        rv_moviesList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        showDialog()

    }

    override fun onDestroy() {
        super.onDestroy()

        UpdateManager.unregister()
    }

    private fun showDialog() {

        adapter?.onItemClickedListener = { movie ->
            (activity as? MainActivity)?.replaceFragment(ShowMovieDetails.newInstance(movie))

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.navigation_savedphoto) {

            startActivity(Intent(context, ShowSavedMovieActivity::class.java))
        } else {
            Toast.makeText(context, "Ready in search", Toast.LENGTH_LONG).show()

        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.moviesfun(newText.toString())
        updateRecyclerview()

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }


}

