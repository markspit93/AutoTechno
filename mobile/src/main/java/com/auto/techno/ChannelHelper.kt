package com.auto.techno

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaDescriptionCompat

object ChannelHelper {

    private val channelList = listOf(
            Channel("di_fm_techno", "techno", "Techno", R.drawable.techno),
            Channel("di_fm_detroit", "detroithousentechno", "Detroit House & Techno", R.drawable.detroit_house_techno),
            Channel("di_fm_dark_techno", "undergroundtechno", "Underground Techno", R.drawable.underground_techno),
            Channel("di_fm_house", "techhouse", "Tech House", R.drawable.tech_house)
    )

    fun createListing(context: Context): ArrayList<MediaItem> {
        val mediaDescListing = arrayListOf<MediaItem>()

        channelList.forEach {
            val mediaDesc = MediaDescriptionCompat.Builder()
                    .setMediaId(it.mediaId)
                    .setMediaUri(Uri.parse(context.getString(R.string.channel_url, it.mediaName)))
                    .setTitle(it.title)
                    .setSubtitle("DI.FM")
                    .setIconBitmap(BitmapFactory.decodeResource(context.resources, it.imageRes))
                    .build()

            mediaDescListing.add(MediaItem(mediaDesc, MediaItem.FLAG_PLAYABLE))
        }

        return mediaDescListing
    }

    fun getImageForMedia(mediaId: String): Int {
        channelList.forEach {
            if (mediaId == it.mediaId) {
                return it.imageRes
            }
        }

        return 0
    }
}
