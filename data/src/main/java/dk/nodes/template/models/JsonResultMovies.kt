package dk.nodes.template.models

import com.google.gson.annotations.SerializedName

data class JsonResultMovies (

    @SerializedName("results")
    var result : ArrayList<Movie>




) {
    override fun toString(): String {
        return "JsonResultMovies(result=$result)"
    }
}