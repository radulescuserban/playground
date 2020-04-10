package tremend.com.shimmertest.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import tremend.com.shimmertest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.navHostFragment)
        bottomNavigation.setupWithNavController(navController)

        getFbInstanceId()
    }

    override fun onSupportNavigateUp()
            = findNavController(R.id.navHostFragment).navigateUp()

    private fun getFbInstanceId() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { Log.d("FB_ID_TOKEN", it.token) }
            .addOnFailureListener { Log.d("FB_ID_TOKEN", "get instanceId failed") }
    }
}
