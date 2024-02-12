package com.example.musicstreamingapp.models

data class CategoriesModel(
    val name: String,
    val coverUrl: String,
    var songs: List<String>
) {
    constructor() : this("","", listOf())
}
