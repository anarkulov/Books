package com.erzhan.books

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VolumeInfo {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("authors")
    @Expose
    var authors: ArrayList<String>? = null

    @SerializedName("publishedDate")
    @Expose
    var publishedDate: String? = null

    @SerializedName("pageCount")
    @Expose
    var pageCount: Int? = null

    @SerializedName("averageRating")
    @Expose
    var averageRating: Double? = null

    @SerializedName("categories")
    @Expose
    var categories: ArrayList<String>? = null

    @SerializedName("imageLinks")
    @Expose
    var imageLinks: ImageLinks? = null

    @SerializedName("infoLink")
    @Expose
    var infoLink: String? = null
}