package dk.nodes.template.presentation.ui.savedmovies

import android.os.Bundle
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import timber.log.Timber

class ShowSavedMovieActivity : BaseActivity() {
    private val viewModel by viewModel<ShowSavedMovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_saved_movie_activity)

        viewModel.savedMoviefun("324552")



        viewModel.viewState.observeNonNull(this) { state ->
            handleNStack(state)

        }



        }


    private fun handleNStack(viewState: MainActivityViewState) {
        viewState.SavedMovie?.let { SavedMovie ->
                Timber.e(SavedMovie.toString())

        }
    }
    }

