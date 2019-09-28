package dk.nodes.template.presentation.ui.main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movieinfodiaglogview.*
import net.hockeyapp.android.UpdateManager
import android.widget.Toast
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieActivity
import timber.log.Timber
import java.text.SimpleDateFormat


class MainActivity : BaseActivity(), View.OnClickListener {
    val saveMovies = HashSet<String>()


    private val viewModel by viewModel<MainActivityViewModel>()
    private val adapter = MoviesAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        see_saved_movie_btn.setOnClickListener(this)

        setupRecyclerview()
        viewModel.viewState.observeNonNull(this) { state ->
            handleNStack(state)
        }





        input_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


                viewModel.moviefun(s.toString())
                setupRecyclerview()

            }
        })
    }

    private fun handleNStack(viewState: MainActivityViewState) {
        viewState.movies?.let { movieList ->
            adapter.addMovies(movieList)
            adapter.notifyDataSetChanged()

        }
    }

    private fun setupRecyclerview() {
        // Creates a vertical Layout Manager
        rv_moviesList.layoutManager = LinearLayoutManager(this)


        // Access the RecyclerView Adapter and load the data into it


        rv_moviesList.adapter = adapter
        rv_moviesList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        showDialog()
    }


    override fun onDestroy() {
        super.onDestroy()
        // If we checked for hockey updates, unregister
        UpdateManager.unregister()
    }


    fun showDialog() {

        adapter.onItemClickedListener = { movie ->

            val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.movieinfodiaglogview)
            val moveName = dialog.findViewById<TextView>(R.id.moviename_txt)
            moveName.setText(movie.name)
            val photo = dialog.movie_images
            Picasso.get().load("https://image.tmdb.org/t/p/w185/" + movie.poster_path).fit().into(photo)
            val language = dialog.language_txt
            language.setText(movie.original_language)
            val releaseDate = dialog.release_txt
            var simpledatetimeformatter = SimpleDateFormat("dd-MM-yyyy")
            releaseDate.setText(simpledatetimeformatter.format(movie.releaseDate))
            val voteAverage = dialog.vote_average_txt
            voteAverage.setText(movie.vote_average)
            val description = dialog.overview_txt
            description.setText(movie.overview)
            val popularity = dialog.popularity
            popularity.setText(movie.popularity)
            val savemovieswitch = dialog.svae_movie_switch

            val backbtn = dialog.findViewById(R.id.back_btn) as Button

            dialog.show()

            savemovieswitch.setOnClickListener {
                Timber.e(savemovieswitch.isChecked.toString())

                if (savemovieswitch.isChecked) {
                    Toast.makeText(applicationContext, "movie is saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "movie is unsaved", Toast.LENGTH_SHORT).show()
                }
            }

            backbtn.setOnClickListener {
                var gson = Gson()

                if (savemovieswitch.isChecked) {
                    val sharedpref = getSharedPreferences("moviesharedpref", Context.MODE_PRIVATE)
                    val editor = sharedpref.edit()
                    val savedobjects = sharedpref.getStringSet("movieslist", HashSet<String>())

                    Timber.e(savedobjects.size.toString())

                    if (savedobjects.size == 0) {
                        saveMovies.add(gson.toJson(movie))
                        editor.putStringSet("movielist", saveMovies)
                        editor.apply()
                    } else {

                        val itemType = object : TypeToken<Movie>() {}.type

                        for (element in savedobjects) {
                            var movie = gson.fromJson<Movie>(element, itemType)
                            saveMovies.add(gson.toJson(movie))

                        }
                        editor.clear()
                        editor.putStringSet("movielist", saveMovies)
                        editor.apply()
                    }
                }

                dialog.dismiss()
            }

        }
    }

    override fun onClick(v: View?) {
        var intent = Intent(this, ShowSavedMovieActivity::class.java)
        startActivity(intent)

    }

}















