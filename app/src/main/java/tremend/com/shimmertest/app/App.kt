package tremend.com.shimmertest.app

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import leakcanary.AppWatcher
import leakcanary.FragmentAndViewModelWatcher
import leakcanary.LeakCanary
import tremend.com.shimmertest.R

class App : Application() {

    var remoteConfig: FirebaseRemoteConfig? = null

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext() : App {
            return instance as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        val watchersToInstall = AppWatcher.appDefaultWatchers(this)
            .filter { it !is FragmentAndViewModelWatcher }
        AppWatcher.manualInstall(
            application = this,
            watchersToInstall = watchersToInstall
        )

        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig?.setConfigSettingsAsync(configSettings)
        remoteConfig?.setDefaultsAsync(R.xml.remote_config_defaults)
    }
}