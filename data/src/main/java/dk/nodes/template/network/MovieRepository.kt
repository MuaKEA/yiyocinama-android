package dk.nodes.template.network

import dk.nodes.template.models.Movie
import dk.nodes.template.models.SavedMovie
import javax.inject.Inject
import kotlin.collections.ArrayList


class MovieRepository @Inject constructor(
        val api: MovieService
) {


    suspend fun getCurrentData(moviename : String): ArrayList<Movie> {
        var movieslisto: ArrayList<Movie> = ArrayList()

        val response = api.getCurrentMovieData(moviename).execute()


        if (response.isSuccessful) {
            val moviesResponse = response.body()

            if (moviesResponse != null) {
                movieslisto.addAll(moviesResponse.result)

            }
        }
        return movieslisto
    }


}


