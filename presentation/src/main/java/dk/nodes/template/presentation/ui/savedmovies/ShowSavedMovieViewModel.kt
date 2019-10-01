package dk.nodes.template.presentation.ui.savedmovies

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import dk.nodes.template.domain.interactors.FetchMoviesInteractor
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.repositories.FetchMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowSavedMovieViewModel @Inject constructor(
    private val nStackPresenter: NStackPresenter,
    private val fetchMoviesInteractor: FetchMoviesInteractor

    ) : BaseViewModel<SavedMoviesViewState>() {
    override val initState: SavedMoviesViewState = SavedMoviesViewState()

    fun fetchSavedMovies(sharedPreferences: SharedPreferences) = viewModelScope.launch {

        state = state.copy(isLoading = true)

        val list = withContext(Dispatchers.IO){

            fetchMoviesInteractor.
            // fetchMoviesInteractor.g(sharedPreferences.getStringSet("movielist",HashSet<String>()))

        }

        state = state.copy(isLoading = false, movies = list)
    }





}



//    fun removeMovie(movieHashSet: MutableSet<String>?): ArrayList<Movie>{
//
//
//        // Do something when user press the positive button
//                    storedMovies?.remove(gson.toJson(movieArrayList[movie]))
//                    movieList.removeAt(movie)
//                    sharedpref.edit().remove("movielist").apply()
//                    sharedpref.edit().apply()
//                    sharedpref.edit().putStringSet("movielist", storedMovies).apply()
//
//
//
//    }















