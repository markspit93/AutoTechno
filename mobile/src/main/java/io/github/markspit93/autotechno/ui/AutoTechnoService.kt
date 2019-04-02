package io.github.markspit93.autotechno.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat
import io.github.markspit93.autotechno.PREF_LAST_MEDIA_ID
import io.github.markspit93.autotechno.channel.ChannelHelper
import io.github.markspit93.autotechno.lazyAndroid
import it.czerwinski.android.delegates.sharedpreferences.stringSharedPreference
import kotlin.properties.Delegates.notNull

class AutoTechnoService : MediaBrowserServiceCompat(), AudioManager.OnAudioFocusChangeListener {

    companion object {
        private const val CONTENT_STYLE_SUPPORTED = "android.media.browse.CONTENT_STYLE_SUPPORTED"
        private const val CONTENT_STYLE_BROWSABLE_HINT  = "android.media.browse.CONTENT_STYLE_BROWSABLE_HINT"
        private const val CONTENT_STYLE_PLAYABLE_HINT = "android.media.browse.CONTENT_STYLE_PLAYABLE_HINT"

        private const val CONTENT_STYLE_LIST_ITEM_HINT_VALUE = 1
        private const val CONTENT_STYLE_GRID_ITEM_HINT_VALUE = 2

        private const val ROOT_ID = "root"
    }

    private var session: MediaSessionCompat by notNull()
    private var lastMediaId by stringSharedPreference(PREF_LAST_MEDIA_ID, "")
    private val playerHolder by lazyAndroid { PlayerHolder(this, session) }

    private val audioManager by lazyAndroid { getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private var audioFocusRequest: AudioFocusRequest? = null

    private val connectedReceiver = ConnectedReceiver()
    private val onAudioNoisyReceiver = OnAudioNoisyReceiver()

    override fun onCreate() {
        super.onCreate()

        session = MediaSessionCompat(this, "AutoTechnoService")
        sessionToken = session.sessionToken

        session.setCallback(MediaSessionCallback())
        session.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)

        playerHolder.createPlayer()

        registerReceiver(connectedReceiver, IntentFilter("com.google.android.gms.car.media.STATUS"))
        registerReceiver(onAudioNoisyReceiver, IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY))
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        val extras = Bundle()
        extras.putBoolean(CONTENT_STYLE_SUPPORTED, true)
        extras.putInt(CONTENT_STYLE_BROWSABLE_HINT, CONTENT_STYLE_LIST_ITEM_HINT_VALUE)
        extras.putInt(CONTENT_STYLE_PLAYABLE_HINT, CONTENT_STYLE_LIST_ITEM_HINT_VALUE)

        return BrowserRoot(ROOT_ID, extras)
    }

    override fun onLoadChildren(parentMediaId: String, result: Result<List<MediaItem>>) {
        if (parentMediaId == ROOT_ID) {
            result.sendResult(ChannelHelper.createBrowsableListing())
        } else {
            result.sendResult(ChannelHelper.createChildrenListing(this, parentMediaId))
        }
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> playerHolder.continuePlaying()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> playerHolder.pausePlaying()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(connectedReceiver)
        unregisterReceiver(onAudioNoisyReceiver)
        playerHolder.releasePlayer()
        session.release()
    }

    private fun getAudioFocus(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(this)
                .setAcceptsDelayedFocusGain(false)
                .build()

            audioManager.requestAudioFocus(audioFocusRequest!!)
        } else {
            audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }
    }

    private fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let { audioManager.abandonAudioFocusRequest(it) }
        } else {
            audioManager.abandonAudioFocus(this)
        }
    }


    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {

        private fun play(mediaId: String) {
            if (getAudioFocus() == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                session.isActive = true
                lastMediaId = mediaId
                playerHolder.startPlaying(ChannelHelper.getChannelForId(mediaId))
            }
        }

        override fun onPlay() {
            play(lastMediaId)
        }

        override fun onPlayFromMediaId(mediaId: String, extras: Bundle) {
            lastMediaId = mediaId
            play(lastMediaId)
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            if (query.isNullOrEmpty()) {
                play(lastMediaId)
            }

            val result = ChannelHelper.searchForChannelMediaId(query!!)

            result?.let {
                play(it)
            } ?: run {
                playerHolder.stopPlaying()
            }
        }

        override fun onSkipToNext() {
            play(ChannelHelper.getNextMediaId(lastMediaId))
        }

        override fun onSkipToPrevious() {
            play(ChannelHelper.getPreviousMediaId(lastMediaId))
        }

        override fun onPause() {
            playerHolder.stopPlaying()
        }

        override fun onStop() {
            abandonAudioFocus()
            session.isActive = false
            playerHolder.stopPlaying()
            stopSelf()
        }
    }

    private inner class ConnectedReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.getStringExtra("media_connection_status") != "media_connected") {
                playerHolder.stopPlaying()
            }
        }
    }

    private inner class OnAudioNoisyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) {
                playerHolder.stopPlaying()
            }
        }
    }
}
