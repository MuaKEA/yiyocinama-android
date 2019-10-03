package dk.nodes.template.repositories

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.Movie
import dk.nodes.template.network.MovieService
import javax.inject.Inject
import kotlin.collections.ArrayList


class MovieRepository @Inject constructor(
        private val api: MovieService,
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
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

    suspend fun getSavedMovies(): ArrayList<Movie> {
        return try {
            val json = sharedPreferences.getString("savedMovies", null)
            val itemType = object : TypeToken<ArrayList<Movie>>() {}.type
            return gson.fromJson<ArrayList<Movie>>(json, itemType)
        } catch (e: Exception) {
            ArrayList()
        }
    }

    suspend fun saveMovies(list: ArrayList<Movie>) {
        try {
            val json = gson.toJson(list)
            sharedPreferences.edit().putString("savedMovies", json).apply()

        } catch (e: Exception) {

        }

    }

}


