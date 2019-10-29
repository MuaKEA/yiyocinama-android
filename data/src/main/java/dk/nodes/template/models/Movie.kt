package dk.nodes.template.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Movie(
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
                parcel.createStringArray())


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

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Movie

                if (genreArray != null) {
                        if (other.genreArray == null) return false
                        if (!genreArray!!.contentEquals(other.genreArray!!)) return false
                } else if (other.genreArray != null) return false

                return true
        }

        override fun hashCode(): Int {
                return genreArray?.contentHashCode() ?: 0
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