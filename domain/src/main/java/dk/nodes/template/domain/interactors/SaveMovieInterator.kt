package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject
import kotlin.collections.ArrayList

class SaveMovieInterator @Inject constructor(
        private val movieRepository: MovieRepository
) :BaseInputAsyncInteractor<Movie,ArrayList<Movie>> {

    override suspend fun invoke(input: Movie) : ArrayList<Movie> {
        return movieRepository.saveMovie(input)
    }


}