package tremend.com.shimmertest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import tremend.com.shimmertest.R
import tremend.com.shimmertest.common.Constants
import tremend.com.shimmertest.network.ImgurImage

class ImageAdapter(private val images: List<ImgurImage?>?) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard_image, parent, false) as View
        return ImageViewHolder(view)
    }

    override fun getItemCount() = images!!.size

    override fun onBindViewHolder(imageViewHolder: ImageViewHolder, p1: Int) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(imageViewHolder.itemView)
            .applyDefaultRequestOptions(requestOptions)
            .load(images?.get(p1)?.link)
            .into(imageViewHolder.itemView.imgurIv)

        ViewCompat.setTransitionName(imageViewHolder.itemView.imgurIv, "image_header")

        val extras = FragmentNavigatorExtras(imageViewHolder.itemView.imgurIv to "image_header")
        val bundle = Bundle()
        bundle.putString(Constants.KEY_IMGUR_URL, images?.get(p1)?.link)
        images?.get(p1)?.id?.let { bundle.putString(Constants.KEY_IMGUR_ID, it) }
        images?.get(p1)?.downVotes?.let { bundle.putInt(Constants.KEY_IMGUR_DOWNS, it) }
        images?.get(p1)?.upVotes?.let { bundle.putInt(Constants.KEY_IMGUR_UPS, it) }
        images?.get(p1)?.points?.let { bundle.putInt(Constants.KEY_IMGUR_POINTS, it) }
        images?.get(p1)?.views?.let { bundle.putInt(Constants.KEY_IMGUR_VIEWS, it) }

        imageViewHolder.itemView.detailsBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_dashboardFragment_to_detailsFragment,
            bundle, null, extras)
        }

        imageViewHolder.itemView.imageTitleTv.text = images?.get(p1)?.description
    }
}