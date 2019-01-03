package tremend.com.shimmertest.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tremend.com.shimmertest.app.App
import tremend.com.shimmertest.common.Constants
import tremend.com.shimmertest.common.SharedPrefsManager
import tremend.com.shimmertest.common.SingleLiveEvent
import tremend.com.shimmertest.network.RetrofitServiceFactory

class LoginViewModel : ViewModel() {

    private lateinit var authentication: SingleLiveEvent<Boolean>
    private val sharedPrefsManager = SharedPrefsManager(App.applicationContext())
    private val retrofitService = RetrofitServiceFactory().createService(sharedPrefsManager)

    fun performLogin(): LiveData<Boolean> {
        if (!::authentication.isInitialized) {
            authentication = SingleLiveEvent()
            loadUsers()
        }
        return authentication
    }

    private fun loadUsers() {
        retrofitService.performLogin(Constants.IMGUR_CLIEND_ID, Constants.IMGUR_RESPONSE_TYPE)
            .enqueue(object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    authentication.postValue(false)
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    authentication.postValue(true)
                }
            })
    }
}