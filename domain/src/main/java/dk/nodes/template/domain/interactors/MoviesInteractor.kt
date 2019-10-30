package dk.nodes.template.domain.interactor

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.domain.interactors.BaseInputAsyncInteractor
import dk.nodes.template.models.Movie
import dk.nodes.template.models.Post
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject


class MoviesInteractor  @Inject constructor(
        private val movieRepository: MovieRepository
): BaseInputAsyncInteractor<String, ArrayList<Movie>> {
    override suspend fun invoke(input: String): ArrayList<Movie> {
        return movieRepository.getCurrentData(input)
    }


}












