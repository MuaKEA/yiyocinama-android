package dk.nodes.template.models

import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("original_title")
        var name: String,
       // @SerializedName("genre_ids")
       // var genre: String,
        @SerializedName("release_date")
        var releaseDate: String,
        @SerializedName("popularity")
        var popularity: String



) {
        override fun toString(): String {
                return "Movie(name='$name', releaseDate='$releaseDate', popularity='$popularity')"
        }
}








