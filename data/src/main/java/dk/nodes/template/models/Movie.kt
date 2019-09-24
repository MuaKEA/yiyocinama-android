package dk.nodes.template.models

import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("original_title")
        var name: String,
        @SerializedName("original_language")
         var original_language: String,
        @SerializedName("release_date")
        var releaseDate: String,
        @SerializedName("popularity")
        var popularity: String,
        @SerializedName("poster_path")
        var poster_path: String,
        @SerializedName("vote_average")
        var vote_average: String,
        @SerializedName("overview")
        var overview: String



) {
        override fun toString(): String {
                return "Movie(name='$name', releaseDate='$releaseDate', popularity='$popularity', poster_path='$poster_path')"
        }
}








