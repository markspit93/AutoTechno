package io.github.markspit93.autotechno.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.STATE_STOPPED
import androidx.core.os.bundleOf
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.metadata.MetadataOutput
import com.google.android.exoplayer2.metadata.icy.IcyInfo
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import io.github.markspit93.autotechno.*
import io.github.markspit93.autotechno.R
import io.github.markspit93.autotechno.channel.Channel
import it.czerwinski.android.delegates.sharedpreferences.stringSharedPreference

class PlayerHolder(private val context: Context,
                   private val session: MediaSessionCompat) : Player.EventListener {

    private val listenerKey by context.stringSharedPreference(PREF_LISTENER_KEY, "")

    private var player: SimpleExoPlayer? = null
    private var channel: Channel? = null

    fun createPlayer() {
        setPlaybackState(STATE_STOPPED)
        player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
        player!!.addListener(this)
    }

    fun startPlaying(channel: Channel) {
        this.channel = channel

        val metadataOutput = MetadataOutput { metadata ->
            for (i in 0 until metadata.length()) {
                val entry = metadata.get(i)
                if (entry is IcyInfo) {
                    val artistTitle = entry.title!!.split(" - ")
                    val artist = artistTitle.getOrNull(0) ?: ""
                    val title = artistTitle.getOrNull(1) ?: ""

                    session.setMetadata(MediaMetadataCompat.Builder()
                            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "${channel.title} DI.FM")
                            .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART,
                                    BitmapFactory.decodeResource(context.resources, channel.imageRes))
                            .build()
                    )

                    return@MetadataOutput
                }
            }
        }

        val userAgent = Util.getUserAgent(context, "AutoTechno")

        val mediaSource = ProgressiveMediaSource
                .Factory(DefaultDataSourceFactory(context, userAgent))
                .createMediaSource(Uri.parse("http://prem4.di.fm:80/${channel.mediaId}?$listenerKey"))

        with(requireNotNull(player)) {
            addMetadataOutput(metadataOutput)
            prepare(mediaSource)
            playWhenReady = true
        }
    }

    fun continuePlaying() {
        requireNotNull(player).playWhenReady = true
    }

    fun pausePlaying() {
        requireNotNull(player).playWhenReady = false
    }

    fun stopPlaying() {
        requireNotNull(player).stop()
    }

    fun releasePlayer() {
        setPlaybackState(STATE_STOPPED)
        player?.removeListener(this)
        player?.release()
        player = null
    }

    private fun setPlaybackState(state: Int) {
        val customAction = PlaybackStateCompat.CustomAction
                .Builder(CUSTOM_ACTION_FAVORITE, context.getString(R.string.action_favorite_name), R.drawable.ic_round_star_border_24dp)
                .setExtras(bundleOf(EXTRA_CHANNEL to channel))
                .build()

        session.setPlaybackState(PlaybackStateCompat.Builder()
                .setState(state, 0, 0f)
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE or
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT)
                .addCustomAction(customAction)
                .build())
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            Player.STATE_READY -> {
                if (playWhenReady) {
                    setPlaybackState(PlaybackStateCompat.STATE_PLAYING)
                } else {
                    setPlaybackState(PlaybackStateCompat.STATE_PAUSED)
                }
            }
            Player.STATE_BUFFERING -> setPlaybackState(PlaybackStateCompat.STATE_BUFFERING)
            Player.STATE_ENDED -> setPlaybackState(PlaybackStateCompat.STATE_STOPPED)
            Player.STATE_IDLE -> setPlaybackState(PlaybackStateCompat.STATE_PAUSED)
            else -> setPlaybackState(PlaybackStateCompat.STATE_NONE)
        }
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        if (isLoading) {
            setPlaybackState(PlaybackStateCompat.STATE_BUFFERING)
        } else {
            setPlaybackState(PlaybackStateCompat.STATE_PLAYING)
        }
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        setPlaybackState(PlaybackStateCompat.STATE_ERROR)
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        // Not implemented
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        // Not implemented
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        // Not implemented
    }

    override fun onPositionDiscontinuity(reason: Int) {
        // Not implemented
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
        // Not implemented
    }

    override fun onSeekProcessed() {
        // Not implemented
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        // Not implemented
    }
}
