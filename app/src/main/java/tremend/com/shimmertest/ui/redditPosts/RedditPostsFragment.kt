package tremend.com.shimmertest.ui.redditPosts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.paging.ExperimentalPagingApi
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_reddit_posts.*
import tremend.com.shimmertest.R

class RedditPostsFragment : Fragment() {

    companion object {
        fun newInstance() = RedditPostsFragment()
    }

    lateinit var redditPostsAdapter: PostsAdapter

    private lateinit var viewModel: RedditPostsViewModel
    private lateinit var disposable: Disposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reddit_posts, container, false)
    }

    @ExperimentalPagingApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        redditPostsAdapter = PostsAdapter()

        viewModel = ViewModelProviders.of(this).get(RedditPostsViewModel::class.java)

        rv.adapter = redditPostsAdapter

        disposable = viewModel.ordersLiveData.subscribe(
            {
                redditPostsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            },
            {

            }
        )

    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }

}