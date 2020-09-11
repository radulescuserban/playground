package tremend.com.shimmertest.network.reddit

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tremend.com.shimmertest.local.room.RedditPostDao
import tremend.com.shimmertest.network.reddit.redditModels.RedditPost

/**
 * Created by Serban Radulescu on 9/11/2020.
 */
@ExperimentalPagingApi
class RedditRemoteMediator(private val postDao: RedditPostDao) : RxRemoteMediator<Int, RedditPost>() {

    private val redditService = RedditService.create()

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, RedditPost>
    ): Single<MediatorResult> {

        var after: String? = null
        var before: String? = null

        when (loadType) {
            LoadType.REFRESH -> {

            }
            LoadType.PREPEND -> {
                return Single.just(MediatorResult.Success(true))
            }
            LoadType.APPEND -> {
                val lastOrder =
                    state.lastItemOrNull() ?: return Single.just(MediatorResult.Success(true))

                after = lastOrder.after
                before = lastOrder.before
            }
        }

        return Single.create { emitter ->
            redditService.getTop("androiddev", 10, after, before)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        if (response.data.children.isNullOrEmpty()) {
                            emitter.onSuccess(MediatorResult.Success(true))
                        } else {
                            val arrayList = ArrayList<RedditPost>()
                            response.data.children.forEach {
                                it.data.after = response.data.after
                                it.data.before = response.data.before
                                arrayList.add(it.data)
                            }

                            postDao.insertAll(arrayList)
                            emitter.onSuccess(MediatorResult.Success(false))
                        }
                    },
                    {

                    }
                )

        }

    }
}