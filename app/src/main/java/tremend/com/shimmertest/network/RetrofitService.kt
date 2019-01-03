package tremend.com.shimmertest.network

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
}