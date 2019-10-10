package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject

class movieSavedInteractor @Inject constructor(
        private val movieRepository: MovieRepository
): BaseInputAsyncInteractor<Movie, Boolean> {


    override suspend fun invoke(input: Movie): Boolean {
        return movieRepository.isMovieSaved(input)
    }

}
