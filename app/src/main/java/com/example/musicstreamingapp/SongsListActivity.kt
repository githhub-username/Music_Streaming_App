package com.example.musicstreamingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstreamingapp.adapter.SongsListAdapter
import com.example.musicstreamingapp.databinding.ActivitySongsListBinding
import com.example.musicstreamingapp.models.CategoriesModel

class SongsListActivity : AppCompatActivity() {

    companion object {
        lateinit var category: CategoriesModel
    }

    lateinit var songListAdapter: SongsListAdapter

    private  lateinit var binding: ActivitySongsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            binding.nameText.text = category.name

            Glide.with(binding.coverImageView).load(category.url)
                .apply(RequestOptions().transform(RoundedCorners(32)))
                .into(binding.coverImageView)


        setupSongRecyclerView()

        }
    }

    fun setupSongRecyclerView() {
        songListAdapter = SongsListAdapter(category.songs)
        binding.recyclerViewSongs.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSongs.adapter = songListAdapter
    }
}