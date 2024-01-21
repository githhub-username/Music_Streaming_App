package com.example.musicstreamingapp

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicstreamingapp.models.SongModel

object ExoPlayer_singleton {

    private var exoPlayer: ExoPlayer? = null
    private var cur_Song: SongModel? = null

    fun getInstance(): ExoPlayer? {
        return exoPlayer
    }

    fun startPlaying(context: Context, song: SongModel) {

        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }

        if(cur_Song != song) {

            // if user touches same song again and again, it shouldn't restart

            cur_Song = song
            cur_Song?.url?.apply {
                val mediaItem = MediaItem.Builder().setUri(this).build()
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.play()
        }


        }
    }

    fun getCurSong(): SongModel? {
        return cur_Song
    }
}
