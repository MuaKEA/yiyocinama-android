package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject

class SaveMovieInterator @Inject constructor(
        private val movieRepository: MovieRepository
) {
    suspend fun saveMovie(movieArrayList: ArrayList<Movie>){

        return movieRepository.saveMovies(movieArrayList)
    }

}