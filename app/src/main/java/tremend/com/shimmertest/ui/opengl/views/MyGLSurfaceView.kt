package tremend.com.shimmertest.ui.opengl.views

import android.content.Context
import android.opengl.GLSurfaceView
import tremend.com.shimmertest.ui.opengl.renderers.MyGLRenderer

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {
        setEGLContextClientVersion(2)
        renderer = MyGLRenderer()
        setRenderer(renderer)
    }
}