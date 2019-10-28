package dk.nodes.template.models

import android.os.Parcel
import android.os.Parcelable
import android.text.BoringLayout
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class Movie(
        @SerializedName("original_title")
        var name: String?,
        @SerializedName("original_language")
         var original_language: String?,
        @SerializedName("release_date")
        var releaseDate: String?,
        @SerializedName("popularity")
        var popularity: String?,
        @SerializedName("poster_path")
        var poster_path: String?,
        @SerializedName("vote_average")
        var vote_average: String?,
        @SerializedName("overview")
        var overview: String?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("genre_ids")
        var genreArray: Array<String>?



) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.createStringArray()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(name)
                parcel.writeString(original_language)
                parcel.writeString(releaseDate)
                parcel.writeString(popularity)
                parcel.writeString(poster_path)
                parcel.writeString(vote_average)
                parcel.writeString(overview)
                parcel.writeString(id)
                parcel.writeStringArray(genreArray)
        }

        override fun describeContents(): Int {
                return 0
        }

        override fun toString(): String {
                return "Movie(name=$name, original_language=$original_language, releaseDate=$releaseDate, popularity=$popularity, poster_path=$poster_path, vote_average=$vote_average, overview=$overview, id=$id, genreArray=${genreArray?.contentToString()})"
        }

        companion object CREATOR : Parcelable.Creator<Movie> {
                override fun createFromParcel(parcel: Parcel): Movie {
                        return Movie(parcel)
                }

                override fun newArray(size: Int): Array<Movie?> {
                        return arrayOfNulls(size)
                }
        }

}

