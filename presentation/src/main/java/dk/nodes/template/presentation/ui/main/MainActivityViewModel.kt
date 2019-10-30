package dk.nodes.template.presentation.ui.main

import androidx.lifecycle.viewModelScope
import dk.nodes.template.domain.interactor.MoviesInteractor
import dk.nodes.template.domain.interactors.*
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.extensions.asResult
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.ui.experimental.ui.main.MovieViewType
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
        private val nStackPresenter: NStackPresenter,
        moviesInteractor: MoviesInteractor,
        private val InternetCheckInteractor: InternetCheckInteractor,
        private val getActionMoviesInteractor:GetActionMoviesInteractor,
        private val getRecommendatonInteractor: GetRecommendatonInteractor,
        private val getDramaMoviesInteractor :GetDramaMoviesInteractor,
        private val getComedyMoviesInteractor: GetComedyMoviesInteractor,
        private val getHorrorMoviesInteractor: GetHorrorMoviesInteractor

) : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()


    private val moviesInteractor = moviesInteractor.asResult()
    private val getRecommendatonInteractors = getRecommendatonInteractor.asResult()
    private val getActionMoviesInteractors = getActionMoviesInteractor.asResult()
    private val getDramaMoviesInteractors = getDramaMoviesInteractor.asResult()
    private val getComedyMoviesInteractors = getComedyMoviesInteractor.asResult()
    private val getHorrorMoviesInteractors =  getComedyMoviesInteractors.asResult()

    fun fetchMovies(movieName: String?, movieViewType: MovieViewType) = viewModelScope.launch(Dispatchers.Main) {

        when(movieViewType) {
            is MovieViewType.Movie -> {
                val result = withContext(Dispatchers.IO) { moviesInteractor.invoke(movieName.toString()) }
                state = mapResult(result)
            }
        }

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
            is Success<*> -> state.copy()
        }
    }

    fun fetchRecomendedMovies(movieViewType: MovieViewType) = viewModelScope.launch(Dispatchers.Main) {

        when(movieViewType) {
            is MovieViewType.Recommended -> {
                val result = withContext(Dispatchers.IO) { getRecommendatonInteractors.invoke() }
                state = mapResult(result)
            }
        }

    }


    fun fetchActionMovies(movieViewType: MovieViewType) = viewModelScope.launch(Dispatchers.Main) {

        when(movieViewType) {
            is MovieViewType.ActionMovie -> {
                val result = withContext(Dispatchers.IO) { getActionMoviesInteractors.invoke() }
                state = mapResult(result)
            }
        }

    }

    fun fetchDramaMovies(movieViewType: MovieViewType) = viewModelScope.launch(Dispatchers.Main) {

        when(movieViewType) {
            is MovieViewType.DramaMovie -> {
                val result = withContext(Dispatchers.IO) { getDramaMoviesInteractors.invoke() }
                state = mapResult(result)
            }
        }

    }


    fun fetchComedyMovies(movieViewType: MovieViewType) = viewModelScope.launch(Dispatchers.Main) {

        when(movieViewType) {
            is MovieViewType.ComedyMovie -> {
                val result = withContext(Dispatchers.IO) { getComedyMoviesInteractors.invoke() }
                state = mapResult(result)
            }
        }

    }

    fun fetchHorrorMovies(movieViewType: MovieViewType) = viewModelScope.launch(Dispatchers.Main) {

        when(movieViewType) {
            is MovieViewType.HorrorMovie -> {
                val result = withContext(Dispatchers.IO) { getComedyMoviesInteractors.invoke() }
                state = mapResult(result)
            }
        }

    }


}