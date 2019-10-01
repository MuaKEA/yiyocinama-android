package dk.nodes.template.repositories

import dk.nodes.template.models.Movie
import dk.nodes.template.models.Post

interface FetchMovieRepository {
    suspend fun getMovies(hashSet : HashSet<String> ): List<Movie>



}