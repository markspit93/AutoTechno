package io.github.markspit93.autotechno.room.dao

import androidx.room.*
import io.github.markspit93.autotechno.channel.Channel

@Dao
interface FavoriteChannelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteChannel: Channel)

    @Query("SELECT * FROM channel")
    fun loadFavorites(): List<Channel>

    @Delete
    fun deleteFavorite(favoriteChannel: Channel)

    @Query("SELECT * FROM channel WHERE mediaId LIKE :mediaId ")
    fun findChannel(mediaId: String): Channel?
}
