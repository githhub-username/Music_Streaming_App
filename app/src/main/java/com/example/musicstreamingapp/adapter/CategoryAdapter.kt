package com.example.musicstreamingapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstreamingapp.SongsListActivity
import com.example.musicstreamingapp.databinding.ItemCategoriesRecyclerBinding
import com.example.musicstreamingapp.models.CategoriesModel
import java.util.Locale.Category

class CategoryAdapter(private val categoryList: List<CategoriesModel>):
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ItemCategoriesRecyclerBinding) : ViewHolder(binding.root) {

        fun bindData(category: CategoriesModel) {
            binding.nameText.text = category.name

            Glide.with(binding.imageView).load(category.coverUrl)
                .apply(RequestOptions().transform(RoundedCorners(32)))
                .into(binding.imageView)

            binding.root.setOnClickListener {
                SongsListActivity.category = category
                binding.root.context.startActivity(Intent(binding.root.context, SongsListActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCategoriesRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(categoryList[position])
    }
}