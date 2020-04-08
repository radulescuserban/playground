package tremend.com.shimmertest.ui.details

import android.animation.ObjectAnimator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_details.*
import tremend.com.shimmertest.R
import tremend.com.shimmertest.common.Constants
import tremend.com.shimmertest.network.ImgurTag

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val transition = TransitionInflater.from(this.activity).inflateTransition(android.R.transition.slide_left)
        val slideRight = TransitionInflater.from(this.activity).inflateTransition(android.R.transition.slide_right)
        val fastOutSlowIn = TransitionInflater.from(activity).inflateTransition(R.transition.image_shared_element_transition)
        fastOutSlowIn.duration = 475

        sharedElementEnterTransition = ChangeBounds().apply {
            enterTransition = fastOutSlowIn
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            returnTransition = fastOutSlowIn
        }


        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        val galleryId = arguments?.getString(Constants.KEY_IMGUR_ID, "")
        val url = arguments?.getString(Constants.KEY_IMGUR_URL)
        val upVotes = arguments?.getInt(Constants.KEY_IMGUR_UPS, 0)
        val downVotes = arguments?.getInt(Constants.KEY_IMGUR_DOWNS, 0)
        val totalPoints = arguments?.getInt(Constants.KEY_IMGUR_POINTS, 0)
        val totalViews = arguments?.getInt(Constants.KEY_IMGUR_VIEWS, 0)
        loadTags(galleryId)
        loadVotes(upVotes, downVotes, totalPoints, totalViews)
        loadImageHeader(url)
        animateViews()
    }

    private fun loadImageHeader(url: String?) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(headerIv.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(url)
            .into(headerIv)
    }

    private fun loadTags(galleryId: String?) {
        viewModel.getTagsLiveData(galleryId).observe(this, Observer {
            if(activity != null) {
                setupTagsRv(it)
                Toast.makeText(activity, "Yey we have tags", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadVotes(upVotes: Int?, downVotes: Int?, totalPoints: Int?, totalViews: Int?) {
        upVotesTv.text = upVotes?.toString()
        downVotesTv.text = downVotes?.toString()
        totalPointsTv.text = totalPoints?.toString()
        totalViewsTv.text = totalViews?.toString()
    }

    private fun animateViews() {
        crossFade(upVotesTv)
        crossFade(downVotesTv)
        crossFade(totalPointsTv)
        crossFade(totalViewsTv)
        translateView(upVotesTv, 150f)
        translateView(downVotesTv, 150f)
        translateView(totalPointsTv, -150f)
        translateView(totalViewsTv, -150f)
    }

    private fun crossFade(view: View) {
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(1000)
                .setListener(null)
        }
    }

    private fun translateView(view: View, value: Float) {
        ObjectAnimator.ofFloat(view, "translationX", value).apply {
            duration = 1000
            start()
        }
    }

    private fun setupTagsRv(tags: List<ImgurTag?>?) {
        val linearLayoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        tagsRv.layoutManager = linearLayoutManager
        val adapter = TagsAdapter(tags)
        tagsRv.adapter = adapter
    }
}
