package dk.nodes.template.models

import com.google.gson.annotations.SerializedName


class ThrillerInfo(

        @SerializedName("key")
        var key: String?,
        @SerializedName("site")
        var site: String?,
        @SerializedName("type")
        var type: String?



) {
        override fun toString(): String {
                return "ThrillerInfo(key=$key, site=$site, type=$type)"
        }
}
