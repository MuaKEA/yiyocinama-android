package dk.nodes.template.presentation.ui.main

import android.widget.Adapter
import androidx.lifecycle.viewModelScope
import dk.nodes.template.models.Movie
import dk.nodes.template.network.MovieRepository
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val nStackPresenter: NStackPresenter,
    private val movieRepository: MovieRepository
) : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()


    fun moviefun() = viewModelScope.launch {
        state = state.copy(isLoading = true)

        val list = withContext(Dispatchers.IO){
            movieRepository.getCurrentData()


        }

        state = state.copy(isLoading = false, movies = list)
    }

}