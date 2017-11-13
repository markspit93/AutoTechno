package com.auto.techno

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import it.czerwinski.android.delegates.sharedpreferences.stringSharedPreference
import kotlin.properties.Delegates.notNull

class AutoTechnoService : MediaBrowserServiceCompat() {

    private var session: MediaSessionCompat by notNull()
    private var lastMediaId by stringSharedPreference("pref_last_media_id", "")
    private val playerHolder: PlayerHolder by lazy { PlayerHolder(this@AutoTechnoService, session) }
    private val connectedReceiver = ConnectedReceiver()

    override fun onCreate() {
        super.onCreate()

        session = MediaSessionCompat(this, "AutoTechnoService")
        sessionToken = session.sessionToken

        session.setCallback(MediaSessionCallback())
        session.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)

        playerHolder.createPlayer()
        playerHolder.setMetaData(ChannelHelper.getChannelForId(lastMediaId))

        registerReceiver(connectedReceiver, IntentFilter("com.google.android.gms.car.media.STATUS"))
    }

    override fun onDestroy() {
        unregisterReceiver(connectedReceiver)
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

    private inner class ConnectedReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.getStringExtra("media_connection_status") != "media_connected") {
                playerHolder.stopPlaying()
            }
        }
    }
}
