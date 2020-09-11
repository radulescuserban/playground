package tremend.com.shimmertest.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tremend.com.shimmertest.network.reddit.redditModels.RedditPost

/**
 * Created by Serban Radulescu on 9/11/2020.
 */

@Dao
interface RedditPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<RedditPost>)

    @Query("SELECT * FROM posts WHERE subreddit = :subreddit")
    fun postsBySubreddit(subreddit: String): PagingSource<Int, RedditPost>

    @Query("DELETE FROM posts WHERE subreddit = :subreddit")
    fun deleteBySubreddit(subreddit: String)

    @Query("SELECT MAX(indexInResponse) + 1 FROM posts WHERE subreddit = :subreddit")
    fun getNextIndexInSubreddit(subreddit: String): Int
}