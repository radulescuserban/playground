package tremend.com.shimmertest.ui.opengl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import tremend.com.shimmertest.ui.opengl.views.MyGLSurfaceView

class OpenGLActivity : AppCompatActivity() {
    private lateinit var myGLSurfaceView: MyGLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myGLSurfaceView = MyGLSurfaceView(this)
        setContentView(myGLSurfaceView)
    }
}