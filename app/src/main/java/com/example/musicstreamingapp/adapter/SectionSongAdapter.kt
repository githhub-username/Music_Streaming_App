package com.example.musicstreamingapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstreamingapp.ExoPlayer_singleton
import com.example.musicstreamingapp.SongPlayingActivity
import com.example.musicstreamingapp.SongsListActivity
import com.example.musicstreamingapp.databinding.ItemSongsRecyclerViewBinding
import com.example.musicstreamingapp.databinding.ItemViewSectionSongBinding
import com.example.musicstreamingapp.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class SectionSongAdapter(private val songIdList: List<String>):
    RecyclerView.Adapter<SectionSongAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ItemViewSectionSongBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(songId: String) {

            FirebaseFirestore.getInstance().collection("songs").document(songId).get()
                .addOnSuccessListener {
                    val song = it.toObject(SongModel::class.java)
                    song?.apply {
                        binding.songTitleView.text = title
                        binding.songSubtitleView.text = subtitle

                        Glide.with(binding.songCoverImage).load(coverUrl)
                            .apply(RequestOptions().transform(RoundedCorners(32)))
                            .into(binding.songCoverImage)

                        binding.root.setOnClickListener {
                            ExoPlayer_singleton.startPlaying(binding.root.context, song)
                            it.context.startActivity(Intent(it.context, SongPlayingActivity::class.java))
                        }
                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemViewSectionSongBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songIdList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(songIdList[position])
    }
}