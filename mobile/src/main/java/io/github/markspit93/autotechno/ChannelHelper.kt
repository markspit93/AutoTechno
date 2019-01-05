package io.github.markspit93.autotechno

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaDescriptionCompat

object ChannelHelper {

    private val channelList = listOf(
        Channel("techno", "Techno", R.drawable.techno),
        Channel("detroithousentechno", "Detroit House & Techno", R.drawable.detroit_house_techno),
        Channel("undergroundtechno", "Underground Techno", R.drawable.underground_techno),
        Channel("progressive", "Progressive", R.drawable.progressive),
        Channel("techhouse", "Tech House", R.drawable.tech_house),
        Channel("house", "House", R.drawable.house)
    )

    fun createListing(context: Context): ArrayList<MediaItem> {
        val mediaDescListing = arrayListOf<MediaItem>()

        channelList.forEach {
            val mediaDesc = MediaDescriptionCompat.Builder()
                    .setMediaId(it.mediaId)
                    .setTitle(it.title)
                    .setSubtitle("DI.FM")
                    .setIconBitmap(BitmapFactory.decodeResource(context.resources, it.imageRes))
                    .build()

            mediaDescListing.add(MediaItem(mediaDesc, MediaItem.FLAG_PLAYABLE))
        }

        return mediaDescListing
    }

    fun getChannelForId(mediaId: String): Channel {
        channelList.forEach {
            if (mediaId == it.mediaId) {
                return it
            }
        }

        return channelList[0]
    }

    fun getNextMediaId(currentMediaId: String): String {
        channelList.indexOf(getChannelForId(currentMediaId)).let {
            return if (it == channelList.lastIndex) {
                channelList[0].mediaId
            } else {
                channelList[it + 1].mediaId
            }
        }
    }

    fun getPreviousMediaId(currentMediaId: String): String {
        channelList.indexOf(getChannelForId(currentMediaId)).let {
            return if (it == 0) {
                channelList[channelList.lastIndex].mediaId
            } else {
                channelList[it - 1].mediaId
            }
        }
    }
}
