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
import javax.inject.Inject
import kotlin.collections.ArrayList


class MovieRepository @Inject constructor(
        private val api: MovieService,
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
) {


    suspend fun getCurrentData(moviename: String): ArrayList<Movie> {
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
            Log.d("movierepo", gson.fromJson<ArrayList<Movie>>(json, itemType).toString())

            return gson.fromJson<ArrayList<Movie>>(json, itemType)
        } catch (e: Exception) {
            ArrayList()
        }
    }

    suspend fun saveMovie(movie: Movie): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()

        try {
            if (getSavedMovies().size != 0) {
                moviesList.add(movie)
                moviesList.addAll(getSavedMovies())
                sharedPreferences.edit().clear().apply()

                Log.d("movierepo", moviesList.toString())
            } else {
                moviesList.add(movie)
            }

            val json = gson.toJson(moviesList)
            sharedPreferences.edit().putString("savedMovies", json).apply()

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

//    suspend fun saveMovieResualts(movieList : ArrayList<Movie>){
//        var gson = Gson()
//        val itemType = object : TypeToken<ArrayList>() {}.type
//
//        if(sharedPreferences.getString("movieCache", "") == "") {
//
//
//
//
//        }
//        sharedPreferences.edit().clear()
//        sharedPreferences.edit().putString("movieCache", gson.toJson(movieList)).apply()
//
//
//    }


    fun isOnlineCheck(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {

            return true

        } else {

            throw IOException("No internet")
        }

    }

}
