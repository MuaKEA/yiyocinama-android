package dk.nodes.template.presentation.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import com.squareup.picasso.Picasso
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movieinfodiaglogview.*
import net.hockeyapp.android.UpdateManager
import android.widget.Toast
import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieActivity
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class MainActivity : BaseActivity(), View.OnClickListener, TextWatcher {


    private val saveMoviesArrayList = ArrayList<Movie>()
    private val viewModel by viewModel<MainActivityViewModel>()
    private val adapter = MoviesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        see_saved_movie_btn.setOnClickListener(this)
        setupRecyclerview()
        input_search.addTextChangedListener(this)

        viewModel.viewState.observeNonNull(this) { state ->
            handleMovies(state)
            handleErrors(state)
        }
    }

    private fun handleMovies(viewState: MainActivityViewState) {
        viewState.movies?.let { movieList ->
            error_view.visibility = View.INVISIBLE
            adapter.addMovies(movieList)
            adapter.notifyDataSetChanged()

        }


    }

    private fun handleErrors(viewState: MainActivityViewState) {
        viewState?.viewError?.let {
            if (it.consumed) return@let
            Glide.with(this).asGif().load(R.drawable.tenor).into(error_view)
            error_view.visibility = View.VISIBLE
            Snackbar.make(rv_moviesList, "No Internet OR No Result", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerview() {
        // Creates a vertical Layout Manager
        rv_moviesList.layoutManager = GridLayoutManager(this, 2)
        // Access the RecyclerView Adapter and load the data into it
        rv_moviesList.adapter = adapter
        rv_moviesList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        showDialog()
    }


    override fun onDestroy() {
        super.onDestroy()
        // If we checked for hockey updates, unregisterak
        UpdateManager.unregister()
    }


    private fun showDialog() {

        adapter.onItemClickedListener = { movie ->

            val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.movieinfodiaglogview)
            dialog.moviename_txt.setText(movie.name)
            Picasso.get().load("https://image.tmdb.org/t/p/w185/" + movie.poster_path).fit().into(dialog.movie_images)
            dialog.language_txt.setText(movie.original_language)

            if (dialog.release_txt.text == "") {
                dialog.release_txt.setText("Unknown")

            } else {

                val localdate = LocalDate.of(movie.releaseDate!!.substring(0, 4).toInt(), movie.releaseDate!!.substring(5, 7).toInt(), movie.releaseDate!!.substring(8, 10).toInt()).format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                dialog.release_txt.setText(localdate.toString())
            }

            dialog.vote_average_txt.setText(movie.vote_average + "/10")
            dialog.overview_txt.setText(movie.overview)
            dialog.popularity.setText(movie.popularity)
            val savemovieswitch = dialog.save_movie_switch
            val backbtn = dialog.findViewById(R.id.back_btn) as Button

            dialog.show()

            savemovieswitch.setOnClickListener {
                Timber.e(savemovieswitch.isChecked.toString())
                showMessage(savemovieswitch)

            }
            backbtn.setOnClickListener {
                saveObject(savemovieswitch, movie)
                dialog.dismiss()
            }

        }
    }

    override fun onClick(v: View?) {
        var intent = Intent(this, ShowSavedMovieActivity::class.java)
        startActivity(intent)

    }

    private fun saveObject(savemovieswitch: Switch, movie: Movie) {

        if (savemovieswitch.isChecked) {
            saveMoviesArrayList.add(movie)
            viewModel.saveMovie(saveMoviesArrayList)

        }
    }

    fun showMessage(savemovieswitch: Switch) {
        if (savemovieswitch.isChecked) {
            Toast.makeText(applicationContext, "movie is saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "movie is unsaved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.moviesfun(s.toString())
        setupRecyclerview()
    }


}















