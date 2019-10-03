package dk.nodes.template.presentation.ui.savedmovies

import androidx.lifecycle.viewModelScope
import dk.nodes.template.domain.interactors.*
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.extensions.asResult
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowSavedMovieViewModel @Inject constructor(
        private val nStackPresenter: NStackPresenter,
        private val deleteMovieInteractor: DeleteMovieInteractor,
        private val getMoviesInteractor: GetMoviesInteractor


) : BaseViewModel<SavedMoviesViewState>() {
    override val initState: SavedMoviesViewState = SavedMoviesViewState()


    fun fetchSavedMovies() = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { getMoviesInteractor.asResult().invoke() }
        state = mapSavedMovies(result)

    }


    fun deleteMovie(movie: Movie) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { deleteMovieInteractor.asResult().invoke(movie) }
        state = mapSavedMovies(result)

    }


    private fun mapSavedMovies(result: CompleteResult<ArrayList<Movie>>): SavedMoviesViewState {
        return when (result) {
            is Success -> state.copy(savedMoviesArrayList = result.data, isLoading = false)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> SavedMoviesViewState()
        }
    }
}
























