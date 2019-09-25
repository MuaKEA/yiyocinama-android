package dk.nodes.template.models

import com.google.gson.annotations.SerializedName

class SavedMovie (
        @SerializedName("belongs_to_collection")
        var jsonreponse : String



) {
        override fun toString(): String {
                return "SavedMovie(jsonreponse='$jsonreponse')"
        }
}