package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.experimental.ui.main.MovieViewType
import dk.nodes.template.presentation.ui.movieDetails.ShowMovieDetailsActivity
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.fragment_search_movies.*
import net.hockeyapp.android.UpdateManager
import timber.log.Timber

private const val ARG_PARAM1 = "param1"


class MovieListFragment : BaseFragment() {

    private lateinit var movieViewType: MovieViewType
    private val viewModel by viewModel<MainActivityViewModel>()
    private var maincontext: Context? = null
    private var adapter: MoviesAdapter? = null
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    fun updateAdapter(searchMovie : String = ""){

        viewModel.fetchMovies(searchMovie,movieViewType = movieViewType)

        adapter?.notifyDataSetChanged()

    }

    fun updateActionAdapter(){
        viewModel.fetchActionMovies(movieViewType = movieViewType)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchSavedMovies(movieViewType = movieViewType)
        viewModel.fetchRecomendedMovies(movieViewType = movieViewType)
        viewModel.fetchActionMovies(movieViewType = movieViewType)
        viewModel.fetchNowPlayingMoves(movieViewType = movieViewType)
        viewModel.fetchPopularMoves(movieViewType = movieViewType )
        viewModel.fetchTopRatedMovies(movieViewType = movieViewType )
        viewModel.fetchComedyMovies(movieViewType = movieViewType)
        viewModel.fetchDramaMovies(movieViewType = movieViewType)
        viewModel.fetchHorrorMovies(movieViewType = movieViewType)
        viewModel.fetchActionMovies(movieViewType = movieViewType)

        viewModel.viewState.observeNonNull(this) { state ->

            handleMovies(state)

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        maincontext =context
        adapter = MoviesAdapter(context, R.layout.movie_recylerview_row)

    }

    override fun onDetach() {
        super.onDetach()
    }



    companion object {

        @JvmStatic
        fun newInstance(type: MovieViewType,param1 : String? = "") =
                MovieListFragment().apply {
                    arguments = Bundle().apply {
                        movieViewType = type
                        putString(ARG_PARAM1, param1)


                    }
                    }

    }




    private fun updateRecyclerview() {
        // Creates a vertical Layout Manager
        rv_moviesList.layoutManager = GridLayoutManager(context, 3)
        // Access the RecyclerView Adapter and load the data into it
        rv_moviesList.adapter = adapter
        adapter?.notifyDataSetChanged()
        showDialog()

    }

    override fun onDestroy() {
        super.onDestroy()
        UpdateManager.unregister()
    }

    private fun showDialog() {
        adapter?.onItemClickedListener = { movie ->
            val intent = Intent(maincontext, ShowMovieDetailsActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)

        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }


    private fun handleMovies(viewState: MainActivityViewState) {
        viewState.movies?.let { movieList ->
            adapter?.addMovies(movieList)
            updateRecyclerview()
        }
    }
}
