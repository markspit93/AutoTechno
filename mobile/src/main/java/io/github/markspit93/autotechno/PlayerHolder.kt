package io.github.markspit93.autotechno

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.STATE_STOPPED
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import it.czerwinski.android.delegates.sharedpreferences.stringSharedPreference
import okhttp3.OkHttpClient
import saschpe.exoplayer2.ext.icy.IcyHttpDataSourceFactory

class PlayerHolder(private val context: Context,
                   private val session: MediaSessionCompat) : Player.EventListener {

    private var player: SimpleExoPlayer? = null

    fun createPlayer() {
        setPlaybackState(STATE_STOPPED)
        player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
        player!!.addListener(this)
    }

    fun startPlaying(channel: Channel) {
        val client = OkHttpClient.Builder().build()

        val icyHttpDataSourceFactory = IcyHttpDataSourceFactory.Builder(client)
                .setIcyHeadersListener { icyHeaders ->
                    Log.d("AutoTechno", "onIcyHeaders: %s".format(icyHeaders.toString()))
                }
                .setIcyMetadataChangeListener { icyMetadata ->
                    Log.d("AutoTechno", "onIcyMetaData: %s".format(icyMetadata.toString()))

                    val artistTitle = icyMetadata.streamTitle.split(" - ")
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
                }
                .build()

        val dataSourceFactory = DefaultDataSourceFactory(context, null, icyHttpDataSourceFactory)

        val listenerKey = context.stringSharedPreference(PREF_LISTENER_KEY, "")
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(DefaultExtractorsFactory())
                .createMediaSource(Uri.parse("http://prem4.di.fm:80/${channel.mediaId}?$listenerKey"))

        requireNotNull(player).apply {
            prepare(mediaSource)
            playWhenReady = true
        }

        setMetaData(channel)
    }

    fun setMetaData(channel: Channel) {
        session.setMetadata(MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "DI.FM")
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "${channel.title} Channel")
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, BitmapFactory.decodeResource(context.resources, channel.imageRes))
                .build())
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
        session.setPlaybackState(PlaybackStateCompat.Builder()
                .setState(state, 0, 0f)
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE or
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT)
                .build())
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            Player.STATE_READY -> setPlaybackState(PlaybackStateCompat.STATE_PLAYING)
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
