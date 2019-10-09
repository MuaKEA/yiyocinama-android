package dk.nodes.template.presentation.ui.main

import android.content.Context
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

class MainActivityViewModel @Inject constructor(
        private val nStackPresenter: NStackPresenter,
        moviesInteractor: MoviesInteractor,
        private val saveMovieInterator: SaveMovieInterator,
        private val InternetCheckInteractor: InternetCheckInteractor,
        private val movieSavedInteractor: movieSavedInteractor,
        private val deleteMovieInteractor: DeleteMovieInteractor

) : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()


    private val moviesInteractor = moviesInteractor.asResult()

    fun moviesfun(moviaName: String) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { moviesInteractor.invoke(moviaName) }
        state = mapResult(result)

    }

    private fun mapResult(result: CompleteResult<ArrayList<Movie>>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(movies = result.data, isLoading = false, viewError = null)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }

    }

    fun saveMovie(movie: Movie) = viewModelScope.launch {

        val result = withContext(Dispatchers.IO) { saveMovieInterator.asResult().invoke(movie) }
        state = mapSaveMovie(result)

    }
//
//    fun movieSavedCheck(movie: Movie) = viewModelScope.launch {
//
//        val result = withContext(Dispatchers.IO) { movieSavedInteractor.asResult().invoke(movie) }
//        state = mapMovieSavedCheck(result)
//
//    }
//
//    private fun mapMovieSavedCheck(result: CompleteResult<Boolean>): MainActivityViewState {
//            return when (result) {
//                is Success -> state.copy(isSaved = result.data, isLoading = false)
//                is Fail -> state.copy(
//                        viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
//                        isLoading = false
//                )
//                else -> MainActivityViewState()
//            }

    private fun mapSaveMovie(result: CompleteResult<ArrayList<Movie>>): MainActivityViewState {
        return when (result) {
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }
    }

     fun isDeviceOnlineCheck() = viewModelScope.launch {

        val result = withContext(Dispatchers.IO) { InternetCheckInteractor.asResult().invoke() }
        state = isDeviceOnline(result)

    }

    private fun isDeviceOnline(result: CompleteResult<Boolean>): MainActivityViewState {
        return when (result) {
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { deleteMovieInteractor.asResult().invoke(movie) }
        state = mapSavedMovies(result)

    }

    private fun mapSavedMovies(result: CompleteResult<ArrayList<Movie>>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(movies = result.data, isLoading = false)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }
    }

}