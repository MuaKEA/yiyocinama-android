package dk.nodes.template.domain.interactors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.FetchMovieRepository
import dk.nodes.template.repositories.PostRepository
import javax.inject.Inject


class FetchMoviesInteractor @Inject constructor(
        private val fetchMovieRepository: FetchMovieRepository)

    suspend fun getMovie(movieHashSet: MutableSet<String>?): ArrayList<Movie> {
        var movieArrayList = ArrayList<Movie>()
        val gson = Gson()
        val itemType = object : TypeToken<Movie>() {}.type

        if (!movieHashSet!!.isEmpty()) {
            for (element in movieHashSet) {
                var movie = gson.fromJson<Movie>(element, itemType)
                movieArrayList.add(movie)
            }
        }
        return movieArrayList
    }


