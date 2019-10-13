package dk.nodes.template.presentation.ui.savedmovies

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import kotlinx.android.synthetic.main.show_saved_movie_fragment.*
import timber.log.Timber
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import dk.nodes.template.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie_search.*


class ShowSavedMovieActivity : BaseFragment() {
    private val viewModel by viewModel<ShowSavedMovieViewModel>()
    private var mainActivitycontext : Context? = null
    private var listener: OnFragmentInteractionListener? = null
    private var adapter : SavedMoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.show_saved_movie_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchSavedMovies()

        adapter = mainActivitycontext?.let { SavedMoviesAdapter(it) } ?: return

        viewModel.viewState.observeNonNull(this) { state ->
            handleMovies(state)
            handleErrors(state)

        }

        adapter?.onItemClickedListener = { moviePosition ->

            Timber.e(moviePosition.toString() + " index")
            val movie = adapter?.movies?.get(moviePosition)
            // Initialize a new instance of
            val builder = AlertDialog.Builder(mainActivitycontext)
            // Set the alert dialog title
            builder.setTitle("delete movie")
            // Display a message on alert dialog
            builder.setMessage("Are you sure, you want to delete " + movie?.name)
            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("YES") { dialog, which ->
                viewModel.deleteMovie(movie!!)

            }

            // Display a negative button on alert dialog
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()
            // Display the alert dialog on app interface
            dialog.show()
        }

        savedmovie_rv.adapter = adapter
        savedmovie_rv.layoutManager = LinearLayoutManager(mainActivitycontext)
        // Access the RecyclerView Adapter and load the data into it rv_moviesList.adapter = adapter
        savedmovie_rv.addItemDecoration(DividerItemDecoration(mainActivitycontext, DividerItemDecoration.VERTICAL))

    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivitycontext = context
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment savedMoviesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                ShowSavedMovieActivity().apply {

                }
    }


fun updateAdapter(movieArrayList: ArrayList<Movie>) {
    Timber.e(movieArrayList.toString())
    adapter?.addMovies(movieArrayList)
    adapter?.notifyDataSetChanged()

}

private fun handleMovies(viewState: SavedMoviesViewState) {
    viewState.savedMoviesArrayList.let { movieList ->
        if (movieList != null) {
            Timber.e(movieList.toString())
            updateAdapter(movieList)
        }
    }
}

private fun handleErrors(viewState: SavedMoviesViewState) {
    viewState?.viewError?.let {
        if (it.consumed) return@let
        Snackbar.make(rv_moviesList, "Error : Movie was not Deleted", Snackbar.LENGTH_LONG).show()
    }
}
    }


