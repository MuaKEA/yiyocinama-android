package dk.nodes.template.presentation.ui.savedmovies

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dk.nodes.template.presentation.injection.ViewModelKey
import dk.nodes.template.presentation.ui.main.MainActivity
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.sample.SampleBuilder


@Module
internal abstract class ShowSavedMovieBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(ShowSavedMovieViewModel::class)
    abstract fun bindMainActivityViewMode(viewModel: ShowSavedMovieViewModel): ViewModel

    @ContributesAndroidInjector(
            modules = [
                SampleBuilder::class
            ]
    )
    internal abstract fun showSavedMovieActivity(): ShowSavedMovieActivity
}


