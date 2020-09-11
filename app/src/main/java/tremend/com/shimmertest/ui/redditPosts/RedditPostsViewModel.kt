package tremend.com.shimmertest.ui.redditPosts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import io.reactivex.Flowable
import tremend.com.shimmertest.app.App
import tremend.com.shimmertest.local.room.RoomDb
import tremend.com.shimmertest.network.reddit.RedditRemoteMediator
import tremend.com.shimmertest.network.reddit.RedditService
import tremend.com.shimmertest.network.reddit.redditModels.RedditPost

class RedditPostsViewModel : ViewModel() {

    @ExperimentalPagingApi
    val ordersPager = Pager<Int, RedditPost>(
        PagingConfig(10, 10, false, 10),
        null,
        RedditRemoteMediator(RoomDb.getInstance(App.applicationContext()).posts()),
        { RoomDb.getInstance(App.applicationContext()).posts().postsBySubreddit("androiddev") }
    )

    @ExperimentalPagingApi
    val ordersLiveData: Flowable<PagingData<RedditPost>> = ordersPager.flowable
}