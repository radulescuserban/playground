package tremend.com.shimmertest.network

import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tremend.com.shimmertest.app.App
import tremend.com.shimmertest.common.Constants
import tremend.com.shimmertest.common.SharedPrefsManager

class RetrofitServiceFactory {

    fun createService(sharedPrefsManager: SharedPrefsManager): RetrofitService {
        val clientId = Constants.IMGUR_CLIEND_ID
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)

        val headerInterceptor = Interceptor { chain ->
            val headersBuilder = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-type", "application/json")
                .addHeader("Authorization", "Client-ID $clientId")
            chain.proceed(headersBuilder.build())
        }

        okHttpClientBuilder.run {
            addInterceptor(headerInterceptor)
            addInterceptor(ChuckInterceptor(App.applicationContext()))
        }

        val okHttpClient = okHttpClientBuilder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(RetrofitService::class.java)
    }


}