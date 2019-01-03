package tremend.com.shimmertest.network

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(val data: T, val success: Boolean, val status: String)


data class ImgurImage(var title: String?, var description: String?, val type: String?,
                      var link: String?,
                      @SerializedName("in_gallery") val inGallery: Boolean?,
                      @SerializedName("ups") var upVotes: Int?,
                      @SerializedName("downs") var downVotes: Int?,
                      var points: Int?,
                      var views: Int?)

data class ImgurItem(val title: String?, val description: String?,
                     @SerializedName("is_album") val isAlbum: Boolean?,
                     val link: String?,
                     @SerializedName("in_gallery") val inGallery: Boolean?, val images: List<ImgurImage>?,
                     @SerializedName("ups") var upVotes: Int?,
                     @SerializedName("downs") var downVotes: Int?,
                     var points: Int?,
                     var views: Int?)

data class ImgurData(val name: String?, @SerializedName("display_name") val displayName: String?,
                     @SerializedName("background_is_animated") val backgroundIsAnimated: Boolean?,
                     @SerializedName("thumbnail_is_animated") val thumbnailIsAnimated: Boolean?,
                     val description: String?, val items: List<ImgurItem>?)