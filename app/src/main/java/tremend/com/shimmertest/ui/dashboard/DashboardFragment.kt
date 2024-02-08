package tremend.com.shimmertest.ui.dashboard

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import tremend.com.shimmertest.R
import tremend.com.shimmertest.app.App


class DashboardFragment : Fragment() {

    companion object {
        const val TAG = "DashboardFragment"
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashboardViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        getRemoteConfigValues()

        Log.d(TAG, "onStart")
        observeViewModel()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private fun observeViewModel() {
        viewModel.getImagesResponseLiveData().observe(this, Observer {
            val adapter = ImageAdapter(it)
            recyclerView.adapter = adapter
        })
    }

    private fun getRemoteConfigValues() {
        App.applicationContext().remoteConfig
            ?.fetchAndActivate()
            ?.addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    val remoteConfig = task.result
                    Toast.makeText(
                        activity!!, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT
                    ).show()

                    testDefaultConfig()
                } else {
                    Toast.makeText(
                        activity!!, "Fetch failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun testDefaultConfig() {
        val testString = App.applicationContext().remoteConfig
            ?.getString("remote_string_test")
        Log.d(TAG, testString ?: "")

        val testAb = App.applicationContext().remoteConfig
            ?.getString("ab_test")
        Log.d(TAG, testAb ?: "")
    }

}
