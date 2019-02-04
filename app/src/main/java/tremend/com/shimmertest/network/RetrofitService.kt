package tremend.com.shimmertest.network

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("/oauth2/authorize")
    fun performLogin(@Query("client_id") clientId: String,
                     @Query("response_type") responseType: String): Call<Boolean>

    @GET("/3/gallery/t/{tagName}")
    fun getImagesByTag(@Path("tagName") tagName: String) : Call<BaseResponse<ImgurData>>

    @GET("/3/gallery/{galleryHash}/tags")
    fun getGalleryTags(@Path("galleryHash") galleryHash: String?) : Call<BaseResponse<List<ImgurTag>>>

    @GET("3/tags")
    fun getAllTags() : Observable<BaseResponse<String>>
}