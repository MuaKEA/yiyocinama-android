package dk.nodes.template.models

import com.google.gson.annotations.SerializedName

class SavedMovie (
        @SerializedName("adult")
        var jsonreponse : String



) {
        override fun toString(): String {
                return "SavedMovie(jsonreponse='$jsonreponse')"
        }
}