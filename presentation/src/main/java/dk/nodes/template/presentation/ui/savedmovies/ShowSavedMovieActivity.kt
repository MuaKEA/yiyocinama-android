package dk.nodes.template.presentation.ui.savedmovies

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_saved_movie_activity.*
import timber.log.Timber
import android.app.AlertDialog
import android.view.Window
import com.google.android.material.snackbar.Snackbar


class ShowSavedMovieActivity : BaseActivity() {
    private val viewModel by viewModel<ShowSavedMovieViewModel>()
    private val adapter = SavedMoviesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_saved_movie_activity)


        viewModel.fetchSavedMovies()

        viewModel.viewState.observeNonNull(this) { state ->
            handleMovies(state)
            handleErrors(state)

        }

        adapter.onItemClickedListener = { moviePosition ->

            Timber.e(moviePosition.toString() + " index")
            val movie = adapter.movies.get(moviePosition)
            // Initialize a new instance of
            val builder = AlertDialog.Builder(this)
            // Set the alert dialog title
            builder.setTitle("delete movie")
            // Display a message on alert dialog
            builder.setMessage("Are you sure, you want to delete " + movie.name)
            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("YES") { dialog, which ->
                viewModel.deleteMovie(movie)

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
        savedmovie_rv.layoutManager = LinearLayoutManager(this)
        // Access the RecyclerView Adapter and load the data into it rv_moviesList.adapter = adapter
        savedmovie_rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

    fun updateAdapter(movieArrayList: ArrayList<Movie>) {
        Timber.e(movieArrayList.toString())
        adapter.addMovies(movieArrayList)
        adapter.notifyDataSetChanged()

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
















