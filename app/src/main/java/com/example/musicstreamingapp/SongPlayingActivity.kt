package com.example.musicstreamingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstreamingapp.databinding.ActivitySongPlayingBinding

class SongPlayingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongPlayingBinding
    lateinit var exoPlayer: ExoPlayer

    var playerListener = object: Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            gifStartStop(isPlaying)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ExoPlayer_singleton.getCurSong()?.apply {
            binding.songTitleView.text = title
            binding.songSubtitleView.text = subtitle

            Glide.with(binding.songCoverImage).load(coverUrl)
                .circleCrop()
                .into(binding.songCoverImage)

            Glide.with(binding.songViewGifEffect).load(R.drawable.meda_playing_gif)
                .circleCrop()
                .into(binding.songViewGifEffect)

            exoPlayer = ExoPlayer_singleton.getInstance()!!
            binding.playerView.player = exoPlayer

            binding.playerView.showController()

            exoPlayer.addListener(playerListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        exoPlayer?.removeListener(playerListener)
    }

    fun gifStartStop(show: Boolean) {
        if(show) {
            binding.songViewGifEffect.visibility = View.VISIBLE
        }
        else {
            binding.songViewGifEffect.visibility = View.INVISIBLE
        }
    }
}