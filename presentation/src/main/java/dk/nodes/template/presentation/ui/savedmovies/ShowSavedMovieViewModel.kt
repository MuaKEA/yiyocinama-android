package dk.nodes.template.presentation.ui.savedmovies

import androidx.lifecycle.viewModelScope
import dk.nodes.template.domain.interactors.GetMoviesInteractor
import dk.nodes.template.domain.interactors.MoviesInteractor
import dk.nodes.template.domain.interactors.SaveMovieInterator
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.extensions.asResult
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowSavedMovieViewModel @Inject constructor(
    private val nStackPresenter: NStackPresenter,
   private val moviesInteractor: MoviesInteractor,
    private val saveMovieInterator: SaveMovieInterator,
    private val getMoviesInteractor: GetMoviesInteractor


    ) : BaseViewModel<SavedMoviesViewState>() {
    override val initState: SavedMoviesViewState = SavedMoviesViewState()

    fun fetchSavedMovies() = viewModelScope.launch {
        state = state.copy(isLoading = true)

        val list = withContext(Dispatchers.IO) {

            moviesInteractor.asResult().invoke("lknlk")
            getMoviesInteractor.getmovies()



        }
            state = state.copy(isLoading = false, savedMoviesArrayList = list)



    }
    fun saveMovieToSharedprefences(movieArrayList: ArrayList<Movie>) = viewModelScope.launch {

            state = state.copy(isLoading = true)

            val list = withContext(Dispatchers.IO) {

                saveMovieInterator.saveMovie(movieArrayList)


            }
            state = state.copy(isLoading = false)





        }
}
















