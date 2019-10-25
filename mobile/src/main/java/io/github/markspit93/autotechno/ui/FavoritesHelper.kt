package io.github.markspit93.autotechno.ui

import android.content.Context
import io.github.markspit93.autotechno.channel.Channel
import io.github.markspit93.autotechno.lazyAndroid
import io.github.markspit93.autotechno.room.AutoTechnoDatabase

class FavoritesHelper(context: Context) {

    private val database by lazyAndroid { AutoTechnoDatabase.getInstance(context) }

    fun getFavoriteChannels() = database.favoriteDao().loadFavorites().toMutableList()

    fun isFavorited(channel: Channel): Boolean {
        return database.favoriteDao().findChannel(channel.mediaId) != null
    }

    fun addFavorite(channel: Channel) {
        database.favoriteDao().insertFavorite(channel)
    }

    fun deleteFavorite(channel: Channel) {
        database.favoriteDao().deleteFavorite(channel)
    }
}
