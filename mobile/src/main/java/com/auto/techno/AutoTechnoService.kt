package com.auto.techno

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import kotlin.properties.Delegates.notNull

class AutoTechnoService : MediaBrowserServiceCompat() {

    private var session: MediaSessionCompat by notNull()
    private var lastMediaId: String = ""
    private val playerHolder: PlayerHolder by lazy { PlayerHolder(this@AutoTechnoService, session) }

    override fun onCreate() {
        super.onCreate()

        session = MediaSessionCompat(this, "AutoTechnoService")
        sessionToken = session.sessionToken

        session.setCallback(MediaSessionCallback())
        session.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)

        playerHolder.createPlayer()
    }

    override fun onDestroy() {
        playerHolder.releasePlayer()
        session.release()
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot("root", null)
    }

    override fun onLoadChildren(parentMediaId: String, result: Result<List<MediaItem>>) {
        result.sendResult(ChannelHelper.createListing(this))
    }

    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {

        private val audioFocusListener = AudioManager.OnAudioFocusChangeListener { }

        override fun onPlay() {
            val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val result = am.requestAudioFocus(audioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                session.isActive = true
                playerHolder.startPlaying(ChannelHelper.getChannelForId(lastMediaId))
            }
        }

        override fun onPlayFromMediaId(mediaId: String, extras: Bundle) {
            lastMediaId = mediaId
            onPlay()
        }

        override fun onPause() {
            playerHolder.stopPlaying()
        }

        override fun onStop() {
            val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            am.abandonAudioFocus(audioFocusListener)

            stopSelf()
            session.isActive = false
            playerHolder.stopPlaying()
        }

        override fun onSkipToNext() {
        }

        override fun onSkipToPrevious() {
        }
    }
}
