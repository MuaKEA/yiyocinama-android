package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject

class FetchTrailerIntepretor @Inject constructor(
            private val movieRepository: MovieRepository): BaseInputAsyncInteractor<String,String> {

    override suspend fun invoke(input: String): String {
        return movieRepository.getTrailerinfo(input)
    }


}