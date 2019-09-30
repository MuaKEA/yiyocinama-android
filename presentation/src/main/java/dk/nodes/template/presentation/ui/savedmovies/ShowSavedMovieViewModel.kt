package dk.nodes.template.presentation.ui.savedmovies

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.Movie
import dk.nodes.template.network.MovieRepository
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ShowSavedMovieViewModel @Inject constructor(
    private val nStackPresenter: NStackPresenter,
    private val movieRepository: MovieRepository

    ) : BaseViewModel<SavedMoviesViewState>() {
    override val initState: SavedMoviesViewState = SavedMoviesViewState()

    fun fetchSavedMovies(sharedPreferences: SharedPreferences) = viewModelScope.launch {



        state = state.copy(isLoading = true)

        val savedmovieinfo = withContext(Dispatchers.IO) {

            getMovie(sharedPreferences.getStringSet("movielist",HashSet<String>()))
        }

           state = state.copy(isLoading = false, movies = savedmovieinfo )


    }


    fun getMovie(movieHashSet: MutableSet<String>?): ArrayList<Movie> {
        var movieArrayList = ArrayList<Movie>()
        val gson = Gson()
        val itemType = object : TypeToken<Movie>() {}.type

        if (!movieHashSet!!.isEmpty()) {
            Timber.e("storedmovies is not empty")

            for (element in movieHashSet) {
                var movie = gson.fromJson<Movie>(element, itemType)
                movieArrayList.add(movie)
            }
        }
        return movieArrayList
    }


    fun removeMovie(){





    }


}













