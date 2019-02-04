package tremend.com.shimmertest.ui.home

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tremend.com.shimmertest.app.App
import tremend.com.shimmertest.common.SharedPrefsManager
import tremend.com.shimmertest.network.RetrofitServiceFactory

class HomeViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailsViewModel"
    }

//    private lateinit var disposable: Disposable
//    private lateinit var disposable2: Disposable
    private val sharedPrefsManager = SharedPrefsManager(App.applicationContext())
    private val retrofitService = RetrofitServiceFactory().createService(sharedPrefsManager)


    fun getAllTags() {
        val observable = retrofitService.getAllTags()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()

    }

    override fun onCleared() {
        super.onCleared()
    }
}