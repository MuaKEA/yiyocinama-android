package dk.nodes.template.presentation.ui.savedmovies

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import dk.nodes.template.presentation.ui.main.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_saved_movie_activity.*
import timber.log.Timber

class ShowSavedMovieActivity : BaseActivity() {
    private val viewModel by viewModel<ShowSavedMovieViewModel>()
    private val adapter = SavedMoviesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_saved_movie_activity)
        var movieArrayList = ArrayList<Movie>()
        val gson = Gson()

        viewModel.viewState.observeNonNull(this) { state ->

        }


        var sharedpref = application.getSharedPreferences("moviesharedpref", Context.MODE_PRIVATE)
        var storedMovies = sharedpref.getStringSet("movielist", HashSet<String>())

        val itemType = object : TypeToken<Movie>() {}.type

        if (!storedMovies.isEmpty()) {


            for (element in storedMovies) {
                var movie = gson.fromJson<Movie>(element, itemType)
                movieArrayList.add(movie)
            }


            Timber.e(movieArrayList.toString())
            savedmovie_rv.adapter = adapter

            savedmovie_rv.layoutManager = LinearLayoutManager(this)
            // Access the RecyclerView Adapter and load the data into it rv_moviesList.adapter = adapter
            savedmovie_rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            adapter.addMovies(movieArrayList)
            adapter.notifyDataSetChanged()

        }

        adapter.onItemClickedListener = { movie ->




        }

        }
    }













