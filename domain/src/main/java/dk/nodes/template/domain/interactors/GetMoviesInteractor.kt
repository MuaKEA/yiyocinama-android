package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject

class GetMoviesInteractor @Inject constructor(
       private val movieRepository: MovieRepository) {

    suspend fun getmovies() : ArrayList<Movie>{
        return movieRepository.getSavedMovies()

    }
}