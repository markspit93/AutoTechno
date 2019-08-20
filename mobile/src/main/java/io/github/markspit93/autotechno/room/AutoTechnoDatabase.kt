package io.github.markspit93.autotechno.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.markspit93.autotechno.channel.Channel
import io.github.markspit93.autotechno.room.dao.FavoriteChannelDao

@Database(
        entities = [Channel::class],
        version = 1,
        exportSchema = false
)
abstract class AutoTechnoDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AutoTechnoDatabase? = null

        fun getInstance(context: Context): AutoTechnoDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AutoTechnoDatabase::class.java, "autotechno.db")
                        .allowMainThreadQueries()
                        .build()
            }

            return requireNotNull(INSTANCE)
        }
    }

    abstract fun favoriteDao(): FavoriteChannelDao
}
