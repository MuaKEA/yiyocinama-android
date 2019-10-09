package dk.nodes.template.domain.interactors

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import dk.nodes.template.models.Movie
import dk.nodes.template.repositories.MovieRepository
import java.io.IOException
import javax.inject.Inject

class InternetCheckInteractor  @Inject constructor
(
        private val context: Context

) : BaseAsyncInteractor<Boolean>
{
    override suspend fun invoke(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


}