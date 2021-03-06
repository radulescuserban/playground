package tremend.com.shimmertest.ui.opengl.renderers

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import tremend.com.shimmertest.ui.opengl.shapes.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {

    private lateinit var triangle: Triangle

    private val MVPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val rotationMatrix = FloatArray(16)
    private val scaleMatrix = FloatArray(16)

    @Volatile
    var angle: Float = 0f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        triangle = Triangle()
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    }

    override fun onDrawFrame(gl: GL10?) {
        val scratch = FloatArray(16)
        val operationsMatrix = FloatArray(16)

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

        Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 0f, -1.0f)
        Matrix.scaleM(scaleMatrix, 0, 0.5f, 0.5f, 0.5f)

        // Calculate the projection and view transformation
        Matrix.multiplyMM(MVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        Matrix.multiplyMM(scratch, 0, MVPMatrix, 0, scaleMatrix, 0)

        triangle.draw(scratch)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        val ratio: Float = width.toFloat() / height.toFloat()

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
    }

}