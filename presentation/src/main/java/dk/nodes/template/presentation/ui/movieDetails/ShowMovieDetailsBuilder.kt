package dk.nodes.template.presentation.ui.movieDetails

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dk.nodes.template.presentation.injection.ViewModelKey


@Module
internal abstract class ShowMovieDetailsBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(ShowMovieDetailsViewModel::class)
    abstract fun bindShowMovieDetailsViewModel(viewModel: ShowMovieDetailsViewModel): ViewModel

    @ContributesAndroidInjector
    internal abstract fun ShowMovieDetails(): ShowMovieDetailsActivity
}