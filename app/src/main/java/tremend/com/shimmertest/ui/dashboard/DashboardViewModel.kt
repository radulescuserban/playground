package tremend.com.shimmertest.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tremend.com.shimmertest.app.App
import tremend.com.shimmertest.common.Constants
import tremend.com.shimmertest.common.SharedPrefsManager
import tremend.com.shimmertest.network.*

class DashboardViewModel : ViewModel() {
    companion object {
        private const val TAG = "DashboardViewModel"
    }

    private lateinit var imageResponseLiveData: MutableLiveData<List<ImgurImage?>>
    private val sharedPrefsManager = SharedPrefsManager(App.applicationContext())
    private val retrofitService = RetrofitServiceFactory().createService(sharedPrefsManager)

    private val query = "city"

    fun getImagesResponseLiveData(): LiveData<List<ImgurImage?>> {
        if (!::imageResponseLiveData.isInitialized) {
            imageResponseLiveData = MutableLiveData()
            loadImages()
        }
        return imageResponseLiveData
    }

    private fun loadImages() {
        retrofitService.getImagesByTag(query).enqueue(object : Callback<BaseResponse<ImgurData>> {

            override fun onFailure(call: Call<BaseResponse<ImgurData>>, t: Throwable) {
                Log.d(TAG, "fail ${t.printStackTrace()}")
            }

            override fun onResponse(call: Call<BaseResponse<ImgurData>>, response: Response<BaseResponse<ImgurData>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "success ${response.body()?.data?.items?.size}")
                    var imagesList = ArrayList<ImgurImage>()

                    response.body()?.data?.items?.forEach { imgurItem: ImgurItem ->
                        if (imgurItem.isAlbum != null && !imgurItem.isAlbum) {
                            val imgurImageTemp = ImgurImage(
                                imgurItem.id,
                                imgurItem.title, imgurItem.description,
                                null, imgurItem.link, imgurItem.inGallery,
                                imgurItem.upVotes, imgurItem.downVotes, imgurItem.points,
                                imgurItem.views
                            )

                            imagesList.add(imgurImageTemp)
                        } else {
                            val galleryList = imgurItem.images
                            galleryList?.let {
                                galleryList.forEach { imgurImage ->
                                    imgurImage.id = imgurItem.id
                                    imgurImage.description = imgurItem.title
                                    imgurImage.downVotes = imgurItem.downVotes
                                    imgurImage.upVotes = imgurItem.upVotes
                                    imgurImage.points = imgurItem.points
                                }

                                imagesList.addAll(galleryList.asIterable())
                            }
                        }
                    }

                    imagesList = imagesList.filter { imgurImage ->
                        imgurImage.type.equals(Constants.IMGUR_IMAGE_TYPE_JPEG) ||
                                imgurImage.type.equals(Constants.IMGUR_IMAGE_TYPE_PNG)
                    } as ArrayList<ImgurImage>

                    Log.d(TAG, "success ${imagesList.size}")
                    imageResponseLiveData.postValue(imagesList)
                } else {
                    onFailure(call, Throwable("fail"))
                }
            }
        })
    }
}
