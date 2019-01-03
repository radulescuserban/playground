package tremend.com.shimmertest.ui.dashboard

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_dashboard_image.view.*
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

        Glide.with(imageViewHolder.itemView.imgurIv)
            .applyDefaultRequestOptions(requestOptions)
            .load(images?.get(p1)?.link)
            .into(imageViewHolder.itemView.imgurIv)

        ViewCompat.setTransitionName(imageViewHolder.itemView.imgurIv, "image_header")
        val extras = FragmentNavigatorExtras(imageViewHolder.itemView.imgurIv to "image_header")
        val bundle = Bundle()
        bundle.putString(Constants.KEY_IMGUR_URL, images?.get(p1)?.link)
        images?.get(p1)?.downVotes?.let { bundle.putInt(Constants.KEY_IMGUR_DOWNS, it) }
        images?.get(p1)?.upVotes?.let { bundle.putInt(Constants.KEY_IMGUR_UPS, it) }
        images?.get(p1)?.points?.let { bundle.putInt(Constants.KEY_IMGUR_POINTS, it) }
        imageViewHolder.itemView.detailsBtn.setOnClickListener { it.findNavController().navigate(R.id.action_dashboardFragment_to_detailsFragment,
            bundle, null, extras) }

        imageViewHolder.itemView.imageTitleTv.text = images?.get(p1)?.description
    }
}