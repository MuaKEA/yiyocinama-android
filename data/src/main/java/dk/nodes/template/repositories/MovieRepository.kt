package dk.nodes.template.repositories

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.nodes.template.models.JsonResultMovies
import dk.nodes.template.models.Movie
import dk.nodes.template.models.ThrillerInfo
import dk.nodes.template.network.MovieService
import retrofit2.Response
import java.lang.NullPointerException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class MovieRepository @Inject constructor(
        private val api: MovieService,
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
) {



    //this api_key needs to be saved away somewere!!
    var api_key  = "4cb1eeab94f45affe2536f2c684a5c9e"
    private lateinit var currentMovies : ArrayList<Movie>




    // this method get Thriller info from a selected movie
    suspend fun getTrailerinfo(movieid: String): String {
        val trailerList: ArrayList<ThrillerInfo> = ArrayList()
        val response = api.getMovieThriller(movieid,api_key).execute()

        if (response.isSuccessful) {

            val moviesResponse = response.body()
            if (moviesResponse != null) {
                trailerList.addAll(moviesResponse.result)

                for (trailer in trailerList) {
                    if (trailer.site == "YouTube" && trailer.type == "Trailer") {
                        return trailer.key.toString()
                    }
                }
            }

        }

        return ""

    }



    /*get the RecommendedMovies, it gets the an movie id randomly from the saved list, if there isnt any,
    then it calls latest movie instead, this insures that the view is filled with movies, instead of an empty view

    */
    suspend fun getRecommendations(): ArrayList<Movie> {

        val movieList: ArrayList<Movie> = getSavedMovies()
        var id= "";
        var response : Response<JsonResultMovies>

        if (movieList.size > 0) {
            response = api.getRecommendations(movieList.random().id.toString(),api_key).execute()

        }else {

             response = api.latestmovies(api_key).execute()

        }
            if (response.isSuccessful) {
                val movieResponse = response.body()
                if (movieResponse != null) {
                    movieList.addAll(movieResponse.result)
                    return movieList
                }
            }



        return movieList

    }

    suspend fun getSpecifiedReconmendations(movieId: String): ArrayList<Movie> {
        val movieList: ArrayList<Movie> = getSavedMovies()


        if (movieList.size > 0) {
            val response = api.getRecommendations(movieId,api_key).execute()
            if (response.isSuccessful) {
                val movieResponse = response.body()
                if (movieResponse != null) {
                    movieList.addAll(movieResponse.result)
                    return movieList
                }
            }
        }

        return movieList

    }

    suspend fun getCurrentData(moviename: String): ArrayList<Movie> {
        Log.d("shadush","->" +  moviename)
        val movieslist: ArrayList<Movie> = ArrayList()
        val response = api.getCurrentMovieData(moviename,api_key).execute()

        if (response.isSuccessful) {
            val moviesResponse = response.body()
            if (moviesResponse != null) {
                movieslist.addAll(moviesResponse.result)
                return completedList(movieslist)
            }
        }
        return movieslist
    }



    suspend fun getPopularMovies(): ArrayList<Movie> {
        val movieslist: ArrayList<Movie> = ArrayList()
        val response = api.getPolularMovies(api_key).execute()

        if (response.isSuccessful) {
            val moviesResponse = response.body()
            if (moviesResponse != null) {
                movieslist.addAll(moviesResponse.result)
                return completedList(movieslist)
            }
        }
        return movieslist
    }


    suspend fun getTopRatedMovies(): ArrayList<Movie> {
        val movieslist: ArrayList<Movie> = ArrayList()
        val response = api.getTopRated(api_key).execute()

        if (response.isSuccessful) {
            val moviesResponse = response.body()
            if (moviesResponse != null) {
                movieslist.addAll(moviesResponse.result)
                return completedList(movieslist)
            }
        }
        return movieslist
    }


    suspend fun getNowPlayingMovies(): ArrayList<Movie> {
        val movieslist: ArrayList<Movie> = ArrayList()
        val response = api.GetNowMoviesData(api_key).execute()

        if (response.isSuccessful) {
            val moviesResponse = response.body()
            if (moviesResponse != null) {
                movieslist.addAll(moviesResponse.result)
                return completedList(movieslist)
            }
        }
        return movieslist
    }






    suspend fun completedList(moviesList: ArrayList<Movie>): ArrayList<Movie> {
        val genreHashmap = getGenres()

        for (movie in moviesList) {

            if (movie.genreArray != null) {
                for (i in 0 until (movie.genreArray?.size!!)) {
                    val s = genreHashmap.get(movie.genreArray!![i].toInt()).toString()
                    movie.genreArray!![i] = s
                }
            }

        }

        currentMovies =moviesList

        return moviesList

    }


    suspend fun getActionMovies() :ArrayList<Movie> {
        var actionMovieList = ArrayList<Movie>()

        for (movie in currentMovies) {
            if (movie.genreArray != null) {
                for (genre in movie.genreArray!!)
                    if (genre == "Action") {
                        actionMovieList.add(movie)
                    }
            }


        }
        return actionMovieList

    }



    suspend fun getdramaMovies() :ArrayList<Movie> {
        var actionMovieList = ArrayList<Movie>()

        for (movie in currentMovies) {
            if (movie.genreArray != null) {
                for (genre in movie.genreArray!!)
                    if (genre == "Drama") {
                        actionMovieList.add(movie)
                    }
            }
        }
        return actionMovieList

    }


    suspend fun getComedyMovies(): ArrayList<Movie> {
        var actionMovieList = ArrayList<Movie>()

        for (movie in currentMovies) {
            if (movie.genreArray != null) {
                for (genre in movie.genreArray!!)
                    if (genre == "Comedy") {
                        actionMovieList.add(movie)
                    }
            }
        }
        return actionMovieList

    }


    suspend fun getHorrorMovies(): ArrayList<Movie> {
        var actionMovieList = ArrayList<Movie>()

        for (movie in currentMovies) {
            if (movie.genreArray != null) {
                for (genre in movie.genreArray!!)
                    if (genre == "Horror") {
                        actionMovieList.add(movie)
                    }
            }
        }
        return actionMovieList

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

    suspend fun saveMovie(movie: Movie): ArrayList<Movie> {
        val moviesList = getSavedMovies()

        try {
            if (!moviesList.contains(movie)) {
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

    suspend fun isMovieSaved(movie: Movie): Boolean {
        try {
                Log.d("speacial" , getSavedMovies().contains(movie).toString() + " <--")
            return getSavedMovies().contains(movie)

        } catch (E: NullPointerException) {
            Log.d("movierepo", "NullPointerException is thrown")
        }
        return false

    }

    @SuppressLint("UseSparseArrays")
    suspend fun getGenres(): HashMap<Int, String> {
        val genrehashmap = HashMap<Int, String>()
        val response = api.getGenres(api_key).execute()
        if (response.isSuccessful) {
            val moviesResponse = response.body()
            if (moviesResponse != null) {

                for (moviegenres in moviesResponse.genreList!!)
                    moviegenres.id?.let { moviegenres.name?.let { it1 -> genrehashmap.put(it, it1) } }
            }
        }
        return genrehashmap

    }

    suspend fun getSemiliarMovies(movieId: String): ArrayList<Movie> {
        val movieList= ArrayList<Movie>()
        val response = api.getSimilarMovies(movieId,api_key).execute()
            if (response.isSuccessful) {
                val movieResponse = response.body()
                if (movieResponse != null) {

                    movieList.addAll(movieResponse.result)
                    return movieList
                }
            }

        return movieList

    }



}





