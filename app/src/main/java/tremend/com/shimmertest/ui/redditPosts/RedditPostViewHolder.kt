package tremend.com.shimmertest.ui.redditPosts

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_dashboard_image.view.*
import tremend.com.shimmertest.R
import tremend.com.shimmertest.network.reddit.redditModels.RedditPost

/**
 * Created by Serban Radulescu on 9/11/2020.
 */

class RedditPostViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val subtitle: TextView = view.findViewById(R.id.subtitle)
    private val score: TextView = view.findViewById(R.id.score)
    private val thumbnail : ImageView = view.findViewById(R.id.thumbnail)
    private var post : RedditPost? = null
    init {
        view.setOnClickListener {
            post?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(post: RedditPost?) {
        this.post = post
        title.text = post?.title ?: "loading"
        subtitle.text = itemView.context.resources.getString(
            R.string.post_subtitle,
            post?.author ?: "unknown")
        score.text = "${post?.score ?: 0}"
        if (post?.thumbnail?.startsWith("http") == true) {
            thumbnail.visibility = View.VISIBLE
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(itemView.context)
                .load(post.thumbnail)
                .into(thumbnail)
        } else {
            thumbnail.visibility = View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): RedditPostViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.reddit_post_item, parent, false)
            return RedditPostViewHolder(view)
        }
    }

    fun updateScore(item: RedditPost?) {
        post = item
        score.text = "${item?.score ?: 0}"
    }
}