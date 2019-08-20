package io.github.markspit93.autotechno.channel

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaDescriptionCompat
import android.net.Uri
import io.github.markspit93.autotechno.MEDIA_ID_FAVORITES

object ChannelHelper {

    private lateinit var currentChannelList: List<Channel>

    fun createBrowsableListing(context: Context): List<MediaItem> {
        val mediaDescListing = arrayListOf<MediaItem>()

        // Add Favorites
        val iconUri = Uri.parse("android.resource://io.github.markspit93.autotechno/drawable/ic_round_star_18dp")
        val favoritesDesc = MediaDescriptionCompat.Builder()
                .setMediaId(MEDIA_ID_FAVORITES)
                .setTitle(context.getString(io.github.markspit93.autotechno.R.string.favorites))
                .setIconUri(iconUri)
                .build()

        mediaDescListing.add(MediaItem(favoritesDesc, MediaItem.FLAG_BROWSABLE))

        // Add Channel Styles
        ChannelList.getStyles().forEach {
            val mediaDesc = MediaDescriptionCompat.Builder()
                    .setMediaId(it)
                    .setTitle(it)
                    .build()

            mediaDescListing.add(MediaItem(mediaDesc, MediaItem.FLAG_BROWSABLE))
        }

        return mediaDescListing
    }

    fun createChildrenListing(context: Context, category: String): List<MediaItem> {
        val mediaDescListing = arrayListOf<MediaItem>()

        ChannelList.getChannelsForStyle(category).let {
            currentChannelList = it

            it.forEach { channel ->
                val mediaDesc = MediaDescriptionCompat.Builder()
                        .setMediaId(channel.mediaId)
                        .setTitle(channel.title)
                        .setIconBitmap(BitmapFactory.decodeResource(context.resources, channel.imageRes))
                        .build()

                mediaDescListing.add(MediaItem(mediaDesc, MediaItem.FLAG_PLAYABLE))
            }
        }

        return mediaDescListing
    }

    fun createFavoriteListing(context: Context, favoriteChannels: List<Channel>): List<MediaItem> {
        currentChannelList = favoriteChannels

        val mediaDescListing = arrayListOf<MediaItem>()

        favoriteChannels.forEach { channel ->
            val mediaDesc = MediaDescriptionCompat.Builder()
                    .setMediaId(channel.mediaId)
                    .setTitle(channel.title)
                    .setIconBitmap(BitmapFactory.decodeResource(context.resources, channel.imageRes))
                    .build()

            mediaDescListing.add(MediaItem(mediaDesc, MediaItem.FLAG_PLAYABLE))
        }

        return mediaDescListing
    }

    fun getChannelForId(mediaId: String): Channel {
        currentChannelList.forEach {
            if (mediaId == it.mediaId) {
                return it
            }
        }

        return currentChannelList[0]
    }

    fun getNextMediaId(currentMediaId: String): String {
        currentChannelList.indexOf(getChannelForId(currentMediaId)).let {
            return if (it == currentChannelList.lastIndex) {
                currentChannelList[0].mediaId
            } else {
                currentChannelList[it + 1].mediaId
            }
        }
    }

    fun getPreviousMediaId(currentMediaId: String): String {
        currentChannelList.indexOf(getChannelForId(currentMediaId)).let {
            return if (it == 0) {
                currentChannelList[currentChannelList.lastIndex].mediaId
            } else {
                currentChannelList[it - 1].mediaId
            }
        }
    }

    fun searchForChannelMediaId(query: String): String? {
        ChannelList.getStyles().forEach {
            ChannelList.getChannelsForStyle(it).forEach { channel ->
                if (channel.title == query) {
                    return channel.mediaId
                }
            }
        }

        return null
    }
}
