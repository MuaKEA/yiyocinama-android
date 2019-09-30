package dk.nodes.template.presentation.ui.main

import androidx.lifecycle.viewModelScope
import dk.nodes.template.repositories.MovieRepository
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val nStackPresenter: NStackPresenter,
    private val movieRepository: MovieRepository
) : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()


    fun moviefun(movieName : String) = viewModelScope.launch {
        state = state.copy(isLoading = true)

        val list = withContext(Dispatchers.IO){
            movieRepository.getCurrentData(movieName)


        }

        state = state.copy(isLoading = false, movies = list)
    }





}