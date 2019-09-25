package dk.nodes.template.network

import dk.nodes.template.models.JsonResultMovies
import dk.nodes.template.models.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("search/movie?api_key=4cb1eeab94f45affe2536f2c684a5c9e")
    fun getCurrentMovieData(@Query("query") movieName: String ): Call<JsonResultMovies>



    // here we need pojo and http interceptor
    //@GET("https://api.themoviedb.org/3/movie/324552?api_key=4cb1eeab94f45affe2536f2c684a5c9e&query=john%20wick")
      //  fun getSavedMovies() : Call <>


}
