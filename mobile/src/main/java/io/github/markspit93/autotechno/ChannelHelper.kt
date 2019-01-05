package io.github.markspit93.autotechno

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaDescriptionCompat

object ChannelHelper {

    private val channelList = listOf(
            Channel("electroswing_hi", "Electro Swing", R.drawable.techno),
            Channel("bassnjackinhouse_hi", "Bass & Jackin' House", R.drawable.techno),
            Channel("synthwave_hi", "Synthwave", R.drawable.techno),
            Channel("club_hi", "Club Sounds", R.drawable.techno),
            Channel("progressivepsy_hi", "Progressive Psy", R.drawable.techno),
            Channel("djmixes_hi", "DJ Mixes", R.drawable.techno),
            Channel("vocalhouse_hi", "Vocal House", R.drawable.techno),
            Channel("edm_hi", "EDM Hits", R.drawable.techno),
            Channel("house_hi", "House", R.drawable.techno),
            Channel("epictrance_hi", "Epic Trance", R.drawable.techno),
            Channel("atmosphericbreaks_hi", "Atmospheric Breaks", R.drawable.techno),
            Channel("chillstep_hi", "Chillstep", R.drawable.techno),
            Channel("melodicprogressive_hi", "Melodic Progressive", R.drawable.techno),
            Channel("vocalchillout_hi", "Vocal Chillout", R.drawable.techno),
            Channel("progressive_hi", "Progressive", R.drawable.techno),
            Channel("trance_hi", "Trance", R.drawable.techno),
            Channel("vocaltrance_hi", "Vocal Trance", R.drawable.techno),
            Channel("futurebass_hi", "Future Bass", R.drawable.techno),
            Channel("liquidtrap_hi", "Liquid Trap", R.drawable.techno),
            Channel("indiebeats_hi", "Indie Beats", R.drawable.techno),
            Channel("chillntropicalhouse_hi", "Chill & Tropical House", R.drawable.techno),
            Channel("indiedance_hi", "Indie Dance", R.drawable.techno),
            Channel("liquiddubstep_hi", "Liquid Dubstep", R.drawable.techno),
            Channel("liquiddnb_hi", "Liquid DnB", R.drawable.techno),
            Channel("drumandbass_hi", "Drum and Bass", R.drawable.techno),
            Channel("electrohouse_hi", "Electro House", R.drawable.techno)
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
