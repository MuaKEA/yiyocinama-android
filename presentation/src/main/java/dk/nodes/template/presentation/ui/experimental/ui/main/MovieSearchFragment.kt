package dk.nodes.template.presentation.ui.experimental.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.movieDetails.ShowMovieDetailsActivity
import kotlinx.android.synthetic.main.fragment_movie_search.*
import net.hockeyapp.android.UpdateManager
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import dk.nodes.template.presentation.ui.main.MoviesAdapter
import timber.log.Timber

private const val ARG_PARAM1 = "param1"

class MovieSearchFragment : BaseFragment() {

    private var param1: String? = null

    private lateinit var movieViewType: MovieViewType
    private val viewModel by viewModel<MainActivityViewModel>()
    private var adapter: MoviesAdapter? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mainContext: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_search, container, false)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = MoviesAdapter(context, R.layout.movie_recylerview_row)
        mainContext = context

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getContens()


    }

    override fun onStart() {
        super.onStart()

        adapter?.notifyDataSetChanged()
    }

    fun getContens() {

        viewModel.isDeviceOnlineCheck()
        viewModel.fetchMovies(param1, movieViewType = movieViewType)
        viewModel.fetchRecomendedMovies(movieViewType = movieViewType)
        viewModel.fetchActionMovies(movieViewType = movieViewType)
        viewModel.fetchDramaMovies(movieViewType = movieViewType)
        viewModel.fetchComedyMovies(movieViewType = movieViewType)
        viewModel.fetchHorrorMovies(movieViewType = movieViewType)
        viewModel.viewState.observeNonNull(this) { state ->

            handleMovies(state)

        }


        adapter?.notifyDataSetChanged()

    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        adapter?.notifyDataSetChanged()

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(movieName: String?, type: MovieViewType) =
                MovieSearchFragment().apply {
                    movieViewType = type

                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, movieName)

                    }
                }
    }

    private fun updateRecyclerview() {
        // Creates a vertical Layout Manager
        rv_moviesList.layoutManager = GridLayoutManager(context, 3)
        // Access the RecyclerView Adapter and load the data into it
        rv_moviesList.adapter = adapter
        showDialog()

    }

    override fun onDestroy() {
        super.onDestroy()
        UpdateManager.unregister()
    }

    private fun showDialog() {
        adapter?.onItemClickedListener = { movie ->
            val intent = Intent(mainContext, ShowMovieDetailsActivity::class.java)
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