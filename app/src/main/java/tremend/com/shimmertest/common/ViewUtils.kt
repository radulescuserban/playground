package tremend.com.shimmertest.common

import android.content.Context

class ViewUtils {

    companion object {
        fun dpToPx(context: Context, value: Float): Float {
            val density = context.resources.displayMetrics.density
            return value * density
        }

        fun pxToDp(context: Context, value: Float): Float {
            val density = context.resources.displayMetrics.density
            return value / density
        }
    }

}