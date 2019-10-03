package dk.nodes.template.presentation.ui.main

import androidx.lifecycle.viewModelScope
import dk.nodes.template.domain.interactors.*
import dk.nodes.template.models.Movie
import dk.nodes.template.models.Post
import dk.nodes.template.presentation.extensions.asResult
import dk.nodes.template.repositories.MovieRepository
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.ui.sample.SampleViewState
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
        private val nStackPresenter: NStackPresenter,
        moviesInteractor: MoviesInteractor,
        private val saveMovieInterator: SaveMovieInterator

) : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()


    private val moviesInteractor = moviesInteractor.asResult()

    fun moviesfun(moviaName : String) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { moviesInteractor.invoke(moviaName) }
        state = mapResult(result)




    }

    private fun mapResult(result: CompleteResult<ArrayList<Movie>>): MainActivityViewState {
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

    fun saveMovie(movieArrayList: ArrayList<Movie>) = viewModelScope.launch {

        state = state.copy(isLoading = true)

        withContext(Dispatchers.IO) {

            saveMovieInterator.saveMovie(movieArrayList)
        }
        state = state.copy(isLoading = false)


    }



}