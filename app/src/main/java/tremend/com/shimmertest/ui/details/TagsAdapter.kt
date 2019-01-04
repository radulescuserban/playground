package tremend.com.shimmertest.ui.details

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.item_tag.view.*
import tremend.com.shimmertest.R
import tremend.com.shimmertest.common.Constants
import tremend.com.shimmertest.network.ImgurTag

class TagsAdapter(private val tags: List<ImgurTag?>?) : RecyclerView.Adapter<TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag, parent, false) as View
        return TagViewHolder(view)
    }

    override fun getItemCount() = tags!!.size

    override fun onBindViewHolder(tagViewHolder: TagViewHolder, p1: Int) {
        tagViewHolder.itemView.tagNameTv.text = tags?.get(p1)?.name
        val bundle = Bundle()
        bundle.putString(Constants.KEY_IMGUR_TAG_NAME, tags?.get(p1)?.name)
        tags?.get(p1)?.followers?.let { bundle.putInt(Constants.KEY_IMGUR_TAG_FOLLOWRS, it) }
        tags?.get(p1)?.totalItems?.let { bundle.putInt(Constants.KEY_IMGUR_TAG_TOTAL_ITEMS, it) }
        tagViewHolder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_detailsFragment_to_tagFragment, bundle)
        }
    }
}