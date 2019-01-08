package tremend.com.shimmertest.ui

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import tremend.com.shimmertest.R

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val navController = findNavController(R.id.secondNavHostFragment)
    }
    override fun onSupportNavigateUp()
            = findNavController(R.id.secondNavHostFragment).navigateUp()
}