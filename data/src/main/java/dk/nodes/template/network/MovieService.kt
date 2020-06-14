package dk.nodes.template.network

import dk.nodes.template.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("search/movie")
    fun getCurrentMovieData(@Query("query") movieName: String , @Query("api_key") apikey: String): Call<JsonResultMovies>

    @GET("movie/{movieId}/videos")
    fun getMovieThriller(@Path("movieId") movieId: String, @Query("api_key") apikey: String ) : Call<ThrillerResualt>


    @GET("movie/{movie_id}/recommendations")
    fun getRecommendations(@Path("movie_id") movie_id: String, @Query("api_key") apikey: String) : Call<JsonResultMovies>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") apikeu: String) : Call<GenresResualt>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") movie_id: String, @Query("api_key") apikey: String) : Call<JsonResultMovies>


    @GET("movie/popular")
    fun getPolularMovies(@Query("api_key") apikey: String): Call<JsonResultMovies>

    @GET("movie/top_rated")
    fun getTopRated(@Query("api_key") apikey: String): Call<JsonResultMovies>

    @GET("movie/now_playing")
    fun GetNowMoviesData(@Query("api_key") apikey: String): Call<JsonResultMovies>

    @GET("movie/now_playing")
    fun latestmovies(@Query("api_key") apikey: String): Call<JsonResultMovies>

}

