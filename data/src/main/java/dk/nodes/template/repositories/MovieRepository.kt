package dk.nodes.template.repositories

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.Movie
import dk.nodes.template.network.MovieService
import java.io.IOException
import java.lang.NullPointerException
import javax.inject.Inject
import kotlin.collections.ArrayList


class MovieRepository @Inject constructor(
        private val api: MovieService,
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
) {


    suspend fun getCurrentData(moviename: String): ArrayList<Movie> {
        val movieslisto: ArrayList<Movie> = ArrayList()

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
            Log.d("movierepo", gson.fromJson<ArrayList<Movie>>(json, itemType).toString())

            return gson.fromJson<ArrayList<Movie>>(json, itemType)
        } catch (e: Exception) {
            ArrayList()
        }
    }

    suspend fun saveMovie(movie: Movie): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()

        try {

            when (getSavedMovies().isEmpty()) {

                true ->
                    moviesList.add(movie)

                false ->
                    if (!getSavedMovies().contains(movie)) {
                        moviesList.add(movie)
                        moviesList.addAll(getSavedMovies())

                    } else {
                        moviesList.add(movie)


                    }
            }
            val json = gson.toJson(moviesList)
            sharedPreferences.edit().clear().putString("savedMovies", json).apply()

            return moviesList
        } catch (e: Exception) {

        }
        return ArrayList()
    }

    suspend fun deleteMovies(movie: Movie): ArrayList<Movie> {
        val movieArrayList = getSavedMovies()
        movieArrayList.remove(movie)
        val json = gson.toJson(movieArrayList)
        sharedPreferences.edit().putString("savedMovies", json).apply()


        return movieArrayList
    }

    suspend fun isMovieSaved(movie: Movie): Boolean {
        try {

            Log.d("movierepo", getSavedMovies().contains(movie).toString())

            return getSavedMovies().contains(movie)

        } catch (E: NullPointerException) {
            Log.d("movierepo", "NullPointerException is thrown")
        }
        return false

    }

}


