package tremend.com.shimmertest.common

import android.app.Application
import android.content.Context

class SharedPrefsManager(context: Application) {
    companion object {
        private const val PREFERENCE_NAME = "preference-imgur-test"
        private const val ACCESS_TOKEN = "access_token"
    }

    private val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN, "")
        set(value) {
            preferences.edit().putString(ACCESS_TOKEN, value).apply()
        }


}