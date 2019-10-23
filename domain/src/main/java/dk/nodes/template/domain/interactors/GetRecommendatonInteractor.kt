package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject

class GetRecommendatonInteractor @Inject constructor(
        private val movieRepository: MovieRepository): BaseInputAsyncInteractor<String,ArrayList<Movie>> {

    override suspend fun invoke(input: String): ArrayList<Movie> {
        return movieRepository.getRecommendations(input)
    }


}