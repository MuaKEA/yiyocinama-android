package dk.nodes.template.domain.interactors

import android.content.Context
import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import javax.inject.Inject

class internetCheckInteractor  @Inject constructor
(
        private val movieRepository: MovieRepository

) : BaseInputAsyncInteractor <Context, Boolean>
{
    override suspend fun invoke(input: Context): Boolean {
        return movieRepository.isOnlineCheck(input)
    }


}