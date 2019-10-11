package dk.nodes.template.repositories

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.Movie
import dk.nodes.template.models.SavedMovie
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
        Log.d("movierepo", "1")
        val response = api.getCurrentMovieData(moviename).execute()
        if (response.isSuccessful) {
            val moviesResponse = response.body()
            Log.d("movierepo", "2")
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
            Log.d("movierepo", "3 -> " + gson.fromJson<ArrayList<Movie>>(json, itemType))


            return gson.fromJson<ArrayList<Movie>>(json, itemType)
        } catch (e: Exception) {
            ArrayList()
        }
    }

    suspend fun saveMovie(movie: Movie): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()

        try {
            Log.d("movierepo", "4 -> " +  moviesList)

            when (getSavedMovies().isEmpty()) {

                true ->

                moviesList.add(movie)
                false ->
                    if (!getSavedMovies().contains(movie)) {
                        moviesList.add(movie)
                        moviesList.addAll(getSavedMovies())

                    }else{
                        moviesList.addAll(getSavedMovies())
                    }
            }
            val json = gson.toJson(moviesList)
            sharedPreferences.edit().putString("savedMovies", json).apply()
            Log.d("movierepo", "4 -> " +  moviesList)

            return moviesList
        } catch (e: Exception) {

        }
        Log.d("movierepo", "5 -> " +  moviesList)

        return ArrayList()
    }

    suspend fun deleteMovies(movie: Movie): ArrayList<Movie> {
        Log.e("movierepo" , "deletemovie is called")
        val movieArrayList = getSavedMovies()
        movieArrayList.remove(movie)
        val json = gson.toJson(movieArrayList)
        sharedPreferences.edit().putString("savedMovies", json).apply()


        return movieArrayList
    }

    suspend fun isMovieSaved(movie: Movie): Boolean {
        try {
            Log.d("movierepo", "5 -> " + getSavedMovies().contains(movie) )

            return getSavedMovies().contains(movie)

        } catch (E: NullPointerException) {
            Log.d("movierepo", "NullPointerException is thrown")
        }
        return false

    }

}


