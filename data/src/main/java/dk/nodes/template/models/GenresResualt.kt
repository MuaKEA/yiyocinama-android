package dk.nodes.template.models

import com.google.gson.annotations.SerializedName

class GenresResualt (
        @SerializedName("genres")
        var genreList : ArrayList<Genre>?


)

class Genre(
        @SerializedName("id")
        var id : Int?,
        @SerializedName("name")
        var name: String?
)