package com.example.musicstreamingapp.models

data class CategoriesModel(
    val name: String,
    val url: String,
    val songs: List<String>
) {
    constructor() : this("","", listOf())
}
