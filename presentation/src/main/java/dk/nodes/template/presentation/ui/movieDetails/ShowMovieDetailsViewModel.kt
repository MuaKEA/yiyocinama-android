package dk.nodes.template.presentation.ui.movieDetails

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dk.nodes.template.domain.interactors.*
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.asResult
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewErrorController
import kotlinx.android.synthetic.main.activity_show_movie_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject


data class ShowMovieDetailsViewModel @Inject constructor(
        private val nStackPresenter: NStackPresenter,
        private val fetchMovieThriler : FetchTrailerIntepretor,
        private val IsMovieSavedInteractor: IsMovieSavedInteractor,
        private val saveMovieInterator: SaveMovieInterator,
        private val deleteMovieInteractor: DeleteMovieInteractor





) : BaseViewModel<ShowMovieDetailsViewState>() {
    override val initState: ShowMovieDetailsViewState = ShowMovieDetailsViewState()


    private val fetchTrailerIntepretor = fetchMovieThriler.asResult()

    fun fetchThrillerUrl(movieId: String) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { fetchTrailerIntepretor.invoke(movieId) }
        state = mapResult(result)

    }

    private fun mapResult(result: CompleteResult<String>): ShowMovieDetailsViewState {
        return when (result) {
            is Success -> state.copy(thrillerurl = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> ShowMovieDetailsViewState()
        }

    }

    fun movieSavedCheck(movie: Movie) = viewModelScope.launch {

        val result = withContext(Dispatchers.IO) {IsMovieSavedInteractor.asResult().invoke(movie) }
        state = mapMovieSavedCheck(result)

    }

    private fun mapMovieSavedCheck(result: CompleteResult<Boolean>): ShowMovieDetailsViewState {
        return when (result) {
            is Success -> state.copy(isMovieSaved = result.data)
            is Fail -> state.copy()
        }
    }

    fun saveMovie(movie: Movie) = viewModelScope.launch {

        val result = withContext(Dispatchers.IO) { saveMovieInterator.asResult().invoke(movie) }
        state = mapSaveMovie(result)

    }


    private fun mapSaveMovie(result: CompleteResult<ArrayList<Movie>>): ShowMovieDetailsViewState {
        return when (result) {
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> state.copy()
        }
    }
    fun deleteMovie(movie: Movie) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { deleteMovieInteractor.asResult().invoke(movie) }
        state = mapSavedMovies(result)

    }

    private fun mapSavedMovies(result: CompleteResult<ArrayList<Movie>>): ShowMovieDetailsViewState {
        return when (result) {
            is Success -> state.copy(movies = result.data, isLoading = false)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> ShowMovieDetailsViewState()
        }
    }


    fun processImage(posterURL : String)  = viewModelScope.launch(Dispatchers.Main)  {
        var backgoundColor : Int?=null

        withContext(Dispatchers.IO) {
          Picasso.get().load("https://image.tmdb.org/t/p/original/" + posterURL)
                  .error(R.drawable.images)
                  .into(object : Target {

                      override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                          Timber.e(errorDrawable.toString() + "<---")

                      }

                      override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                      }


                      override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                          Timber.e("onBitmapLoaded doing something ")
                          Palette.from(bitmap)
                                  .generate(Palette.PaletteAsyncListener { palette ->
                                      val textSwatch = palette!!.dominantSwatch
                                      if (textSwatch == null) {
                                          return@PaletteAsyncListener
                                      }
                                      backgoundColor = textSwatch.rgb
                                  })


                      }

                  })

        }

    }



}
