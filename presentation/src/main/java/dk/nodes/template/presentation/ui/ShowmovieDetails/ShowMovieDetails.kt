package dk.nodes.template.presentation.ui.ShowmovieDetails

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import kotlinx.android.synthetic.main.activity_show_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_search.*
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ShowMovieDetails : BaseActivity(), CompoundButton.OnCheckedChangeListener {
    private val viewModel by viewModel<MainActivityViewModel>()
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_movie_details)


        val intent = intent
        if (intent != null) {
            movie = intent.getParcelableExtra("movie")
            Timber.e(movie.toString())
        }

        viewModel.viewState.observeNonNull(this) { state ->
            handleErrors(state)
            handleSavedMovies(state)
        }

        viewModel.movieSavedCheck(movie!!)

        movieDetails(movie)
    }

    fun movieDetails(movie: Movie?) {
        if (movie != null) {

            moviename_txt.setText(movie.name)
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + movie.poster_path).error(R.drawable.images).fit().into(movie_images)
            language_txt.setText(movie.original_language)

            if (movie.releaseDate == "") {
                release_txt.setText("Unknown")

            } else {

                val localdate = LocalDate.of(movie.releaseDate?.substring(0, 4)?.toInt()!!, movie.releaseDate!!.substring(5, 7).toInt(), movie.releaseDate!!.substring(8, 10).toInt()).format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                release_txt.setText(localdate.toString())
            }

            vote_average_txt.setText(movie.vote_average.toString() + "/10")
            overview_txt.setText(movie.overview)
            popularity.setText(movie.popularity.toString())

            save_movie_switch.setOnCheckedChangeListener(this)
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

    fun showMessage(savemovieswitch: Switch) {
        if (savemovieswitch.isChecked) {
            Toast.makeText(this, "movie is saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "movie is unsaved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val movie = movie ?: return
        if (isChecked) {
            viewModel.saveMovie(movie)
            showMessage(save_movie_switch)


        } else {

            viewModel.deleteMovie(movie)
            showMessage(save_movie_switch)
            viewModel.movieSavedCheck(movie)

        }

    }

    private fun handleSavedMovies(viewState: MainActivityViewState) {
        viewState.let { isMovieSaved ->
            if (viewState.isMovieSaved) {
                save_movie_switch.isChecked = true
                Timber.e(isMovieSaved.toString())

            }
        }
    }
}



