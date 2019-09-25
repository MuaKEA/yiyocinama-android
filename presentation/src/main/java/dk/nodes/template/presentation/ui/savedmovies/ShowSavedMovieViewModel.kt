package dk.nodes.template.presentation.ui.savedmovies

import android.util.Log
import androidx.lifecycle.viewModelScope
import dk.nodes.template.network.MovieRepository
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowSavedMovieViewModel @Inject constructor(
    private val nStackPresenter: NStackPresenter,
    private val movieRepository: MovieRepository
    ) : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()

    fun savedMoviefun(movieId: String) = viewModelScope.launch {

        state = state.copy(isLoading = true)

        val savedmovieinfo = withContext(Dispatchers.IO) {
            movieRepository.getsavedmovies(movieId)

        }

        state = state.copy(isLoading = false, SavedMovie =savedmovieinfo )


    }
}












