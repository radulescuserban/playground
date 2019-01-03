package tremend.com.shimmertest.ui.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_tag.view.*
import tremend.com.shimmertest.R
import tremend.com.shimmertest.network.ImgurTag

class TagsAdapter(private val tags: List<ImgurTag?>?) : RecyclerView.Adapter<TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag, parent, false) as View
        return TagViewHolder(view)
    }

    override fun getItemCount() = tags!!.size

    override fun onBindViewHolder(imageViewHolder: TagViewHolder, p1: Int) {
        imageViewHolder.itemView.tagNameTv.text = tags?.get(p1)?.name
    }
}