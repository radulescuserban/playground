package tremend.com.shimmertest.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tremend.com.shimmertest.app.App
import tremend.com.shimmertest.common.SharedPrefsManager
import tremend.com.shimmertest.network.BaseResponse
import tremend.com.shimmertest.network.ImgurTag
import tremend.com.shimmertest.network.RetrofitServiceFactory

class DetailsViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailsViewModel"
    }

    private val sharedPrefsManager = SharedPrefsManager(App.applicationContext())
    private val retrofitService = RetrofitServiceFactory().createService(sharedPrefsManager)
    private lateinit var tagsResponseLiveData: MutableLiveData<List<ImgurTag?>>

    fun getTagsLiveData(galleryId: String?): LiveData<List<ImgurTag?>> {
        if (!::tagsResponseLiveData.isInitialized) {
            tagsResponseLiveData = MutableLiveData()
            loadTags(galleryId)
        }
        return tagsResponseLiveData
    }

    private fun loadTags(galleryId: String?) {
        retrofitService.getGalleryTags(galleryId).enqueue(object : Callback<BaseResponse<List<ImgurTag>>> {
            override fun onFailure(call: Call<BaseResponse<List<ImgurTag>>>, t: Throwable) {
                Log.d(DetailsViewModel.TAG, "fail ${t.printStackTrace()}")
            }

            override fun onResponse(
                call: Call<BaseResponse<List<ImgurTag>>>,
                response: Response<BaseResponse<List<ImgurTag>>>
            ) {
                if(response.isSuccessful) {
                    if(response.body() != null)
                    tagsResponseLiveData.postValue(response.body()?.data)
                } else {
                    onFailure(call, Throwable("fail"))
                }
            }
        })
    }
}
