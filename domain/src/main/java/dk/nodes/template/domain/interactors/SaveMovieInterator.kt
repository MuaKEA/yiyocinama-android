package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class SaveMovieInterator @Inject constructor(
        private val movieRepository: MovieRepository
) :BaseInputAsyncInteractor<ArrayList<Movie>,Unit> {

    override suspend fun invoke(input: ArrayList<Movie>) {
        return movieRepository.saveMovies(input)
    }


}