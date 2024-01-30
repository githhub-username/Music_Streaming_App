package com.example.musicstreamingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstreamingapp.adapter.CategoryAdapter
import com.example.musicstreamingapp.adapter.SectionSongAdapter
import com.example.musicstreamingapp.databinding.ActivityMainBinding
import com.example.musicstreamingapp.models.CategoriesModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategories()
        setSection("section_1", binding.section1Layout, binding.titleSection1, binding.recyclerViewSection1)
        setSection("section_2", binding.section2Layout, binding.titleSection2, binding.recyclerViewSection2)
        setSection("section_3", binding.section3Layout, binding.titleSection3, binding.recyclerViewSection3)
        setSection("section_4", binding.section4Layout, binding.titleSection4, binding.recyclerViewSection4)
        setSection("section_5", binding.section5Layout, binding.titleSection5, binding.recyclerViewSection5)

    }

    // for categories

    fun getCategories() {
        FirebaseFirestore.getInstance().collection("Category_Songs").get().addOnSuccessListener {
            val categoryList = it.toObjects(CategoriesModel::class.java)
            setupRecyclerView(categoryList)
        }
    }

    fun setupRecyclerView(categoryList: List<CategoriesModel>) {
        categoryAdapter = CategoryAdapter(categoryList)
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCategories.adapter = categoryAdapter
    }

    // for sections

    fun setSection(id: String, section_layout: RelativeLayout, section_title: TextView, section_recycler_view: RecyclerView) {
        FirebaseFirestore.getInstance().collection("sections")
            .document(id).get().addOnSuccessListener {

                val section = it.toObject(CategoriesModel::class.java)
                section?.apply {
                    section_layout.visibility = View.VISIBLE

                    section_title.text = name
                    section_recycler_view.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    section_recycler_view.adapter = SectionSongAdapter(songs)

                    section_layout.setOnClickListener {

                        SongsListActivity.category = section
                        startActivity(Intent(this@MainActivity, SongsListActivity::class.java))
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()

        cur_player_view()
    }

    fun cur_player_view() {

        binding.currentSongPlayerLayout.setOnClickListener {
            startActivity(Intent(this, SongPlayingActivity::class.java))
        }

        ExoPlayer_singleton.getCurSong()?.let {

            binding.currentSongPlayerLayout.visibility = View.VISIBLE
            binding.songTitleView.text = "Now Playing: " + it.title

            Glide.with(binding.songCoverImage).load(it.coverUrl)
                .apply(RequestOptions().transform(RoundedCorners(32))
                ).into(binding.songCoverImage)

        } ?:run {
            binding.currentSongPlayerLayout.visibility = View.GONE
        }
    }
}