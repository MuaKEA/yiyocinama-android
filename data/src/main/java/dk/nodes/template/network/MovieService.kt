package dk.nodes.template.network

import dk.nodes.template.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("search/movie?api_key=4cb1eeab94f45affe2536f2c684a5c9e")
    fun getCurrentMovieData(@Query("query") movieName: String ): Call<JsonResultMovies>

    @GET("movie/{movieId}/videos?api_key=4cb1eeab94f45affe2536f2c684a5c9e")
    fun getMovieThriller(@Path("movieId") movieId: String) : Call<ThrillerResualt>



}
