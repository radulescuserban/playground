package tremend.com.shimmertest.ui.opengl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tremend.com.shimmertest.ui.opengl.views.MyGLSurfaceView

class OpenGLActivity : AppCompatActivity() {
    private lateinit var myGLSurfaceView: MyGLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myGLSurfaceView = MyGLSurfaceView(this)
        setContentView(myGLSurfaceView)
    }

    override fun onStart() {
        super.onStart()
        myGLSurfaceView.onResume()
    }

    override fun onStop() {
        myGLSurfaceView.onPause()
        super.onStop()
    }


}