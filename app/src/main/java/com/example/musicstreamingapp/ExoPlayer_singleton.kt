package com.example.musicstreamingapp

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicstreamingapp.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore

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

            updateViewsCount()

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

    fun updateViewsCount() {
        cur_Song?.id?.let { id ->
            FirebaseFirestore.getInstance().collection("songs")
                .document(id).get()
                .addOnSuccessListener {
                    var latestViewsCount = it.getLong("count")

                    if(latestViewsCount == null) {
                        latestViewsCount = 1L
                    }
                    else {
                        latestViewsCount += 1
                    }

                    FirebaseFirestore.getInstance().collection("songs")
                        .document(id)
                        .update(mapOf("count" to latestViewsCount))
                }
        }
    }
}
