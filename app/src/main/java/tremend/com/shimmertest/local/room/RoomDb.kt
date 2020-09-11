package tremend.com.shimmertest.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tremend.com.shimmertest.network.reddit.redditModels.RedditPost

/**
 * Created by Serban Radulescu on 9/11/2020.
 */

@Database(
    entities = [RedditPost::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDb : RoomDatabase() {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: RoomDb? = null

        fun getInstance(context: Context): RoomDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create the database.
        private fun buildDatabase(context: Context): RoomDb {
            return Room.databaseBuilder(context, RoomDb::class.java, "app_playground.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun posts(): RedditPostDao
}