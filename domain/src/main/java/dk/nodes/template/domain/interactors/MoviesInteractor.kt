package dk.nodes.template.domain.interactors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject


class MoviesInteractor  @Inject constructor(
        private val movieRepository: MovieRepository
) {

    suspend fun getMovies(): ArrayList<Movie> {
        return movieRepository.getSavedMovies()
    }
    suspend fun saveMovieToSharedpref(movieArrayList: ArrayList<Movie>){

        return movieRepository.saveMovies(movieArrayList)
    }
}












